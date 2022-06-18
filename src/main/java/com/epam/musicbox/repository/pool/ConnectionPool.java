package com.epam.musicbox.repository.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final String PROP_PATH = "prop/database";
    private static final String DB_URL = "URL";
    private static final String DB_USER = "USER";
    private static final String DB_PASSWORD = "PASSWORD";
    private static final String DB_DRIVER = "DRIVER";
    private static final String DB_POOL_SIZE = "POOL_SIZE";

    private static final String DRIVER_NOT_FOUND_MSG = "Driver is not found %s";
    private static final String POOL_CREATED_MSG = "Pool created";
    private static final String POOL_CREATION_ERROR_MSG = "Pool creation error";
    private static final String POOL_DESTROYED_MSG = "Pool destroyed";

    private static final Logger logger = LogManager.getLogger();

    private static final AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static final ReentrantLock instanceLock = new ReentrantLock();
    private static final AtomicReference<ConnectionPool> instance = new AtomicReference<>();

    private final ReentrantLock lock;
    private final Semaphore semaphore;
    private final Deque<Connection> connections;

    private ConnectionPool(Deque<Connection> connections) {
        this.connections = connections;
        this.lock = new ReentrantLock();
        this.semaphore = new Semaphore(connections.size());
    }

    private static ConnectionPool createInstance() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(PROP_PATH);
            String url = resourceBundle.getString(DB_URL);
            String user = resourceBundle.getString(DB_USER);
            String password = resourceBundle.getString(DB_PASSWORD);
            String driver = resourceBundle.getString(DB_DRIVER);
            Class.forName(driver);

            int connectionSize = Integer.parseInt(resourceBundle.getString(DB_POOL_SIZE));
            Deque<Connection> connections = new ArrayDeque<>(connectionSize);

            for (int i = 0; i < connectionSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.push(connection);
            }
            return new ConnectionPool(connections);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(DRIVER_NOT_FOUND_MSG + e.getMessage(), e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            try {
                instanceLock.lock();
                if (instanceCreated.compareAndSet(false, true)) {
                    ConnectionPool pool = createInstance();
                    logger.info(POOL_CREATED_MSG);
                    instance.set(pool);
                }
            } catch (Exception e) {
                logger.error(POOL_CREATION_ERROR_MSG, e);
                instanceCreated.set(false);
                throw e;
            } finally {
                instanceLock.unlock();
            }
        }
        return instance.get();
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
            logger.info(POOL_DESTROYED_MSG);
        } finally {
            lock.unlock();
        }
    }
}
