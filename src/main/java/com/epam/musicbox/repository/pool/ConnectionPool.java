package com.epam.musicbox.repository.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    private static final String PROP_PATH = "prop/database";
    private static final String DB_URL = "URL";
    private static final String DB_USER = "USER";
    private static final String DB_PASSWORD = "PASSWORD";
    private static final String DB_DRIVER = "DRIVER";
    private static final String DB_POOL_SIZE = "POOL_SIZE";

    private static final String POOL_CREATED_MSG = "Pool created";
    private static final String POOL_DESTROYED_MSG = "Pool destroyed";
    private static final String DRIVER_NOT_FOUND_MSG = "Driver is not found";
    private static final String POOL_CREATION_ERROR_MSG = "Pool creation error";


    private static final AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static final ReentrantLock instanceLock = new ReentrantLock();
    private static final AtomicReference<ConnectionPool> instance = new AtomicReference<>();

    private final ReentrantLock lock;
    private final Semaphore semaphore;
    private final Deque<ProxyConnection> freeConnections;
    private final Deque<ProxyConnection> busyConnections;

    private ConnectionPool() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(PROP_PATH);
            String url = resourceBundle.getString(DB_URL);
            String user = resourceBundle.getString(DB_USER);
            String password = resourceBundle.getString(DB_PASSWORD);
            String driver = resourceBundle.getString(DB_DRIVER);
            Class.forName(driver);

            int size = Integer.parseInt(resourceBundle.getString(DB_POOL_SIZE));
            Deque<ProxyConnection> connections = new ArrayDeque<>(size);

            for (int i = 0; i < size; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.push(new ProxyConnection(this, connection));
            }
            this.freeConnections = connections;
            this.busyConnections = new ArrayDeque<>(size);
            this.lock = new ReentrantLock();
            this.semaphore = new Semaphore(size);
        } catch (SQLException e) {
            logger.fatal(POOL_CREATION_ERROR_MSG, e);
            throw new ExceptionInInitializerError(e);
        } catch (ClassNotFoundException e) {
            logger.fatal(DRIVER_NOT_FOUND_MSG, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            try {
                instanceLock.lock();
                if (!instanceCreated.get()) {
                    ConnectionPool pool = new ConnectionPool();
                    instance.set(pool);
                    instanceCreated.set(true);
                    logger.info(POOL_CREATED_MSG);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance.get();
    }

    public ProxyConnection getConnection() {
        try {
            lock.lock();
            semaphore.acquire();
            ProxyConnection connection = freeConnections.pop();
            busyConnections.push(connection);
            return connection;
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void release(ProxyConnection connection) {
        try {
            lock.lock();
            if (busyConnections.remove(connection)) {
                freeConnections.push(connection);
            }
            semaphore.release();
        } finally {
            lock.unlock();
        }
    }

    public void destroyPool() {
        try {
            lock.lock();
            freeConnections.addAll(busyConnections);
            busyConnections.clear();
            for (ProxyConnection connection : freeConnections) {
                try {
                    connection.closeConnection();
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
