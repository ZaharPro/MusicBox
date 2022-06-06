package com.epam.musicbox.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final ResourceBundle resourceBundle;
    private static final ReentrantLock instanceLock = new ReentrantLock();
    private static final AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private final ReentrantLock lock;
    private final Semaphore semaphore;
    private final Deque<Connection> connections;

    private ConnectionPool(Deque<Connection> connections) {
        this.connections = connections;
        this.lock = new ReentrantLock();
        this.semaphore = new Semaphore(connections.size());
    }

    static {
        resourceBundle = System.getenv("Env") == null ? ResourceBundle.getBundle("prop/database") : null;
    }

    private static String getProperty(String propertyName) {
        String valueFromEnv = System.getenv(propertyName);
        if (valueFromEnv != null) {
            return valueFromEnv;
        }

        try {
            return resourceBundle.getString(propertyName);
        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Property %s is not found", propertyName));
        }
    }

    private static ConnectionPool createInstance() {
        try {
            String url = getProperty("URL_DB");
            String name = getProperty("NAME");
            String password = getProperty("PASS");
            String driver = getProperty("DRIVER");
            Class.forName(driver);

            int connectionSize = Integer.parseInt(getProperty("POOL_SIZE"));
            Deque<Connection> connections = new ArrayDeque<>(connectionSize);

            for (int i = 0; i < connectionSize; i++) {
                Connection connection = DriverManager.getConnection(url, name, password);
                connections.push(connection);
            }
            return new ConnectionPool(connections);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Driver is not found %s %s", e.getMessage(), e));
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            try {
                instanceLock.lock();
                if (instanceCreated.compareAndSet(false, true)) {
                    instance = createInstance();
                }
            } catch (Exception e) {
                instanceCreated.set(false);
                throw e;
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    Connection take() {
        try {
            lock.lock();
            semaphore.acquire();
            return connections.pop();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    void release(Connection connection) {
        try {
            lock.lock();
            connections.push(connection);
            semaphore.release();
        } finally {
            lock.unlock();
        }
    }

    public Connection getConnection() {
        return new ProxyConnection(this);
    }

    public void destroyPool() {
        try {
            lock.lock();
            for (Connection connection : connections) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            logger.info("Pool destroyed");
        } finally {
            lock.unlock();
        }
    }
}
