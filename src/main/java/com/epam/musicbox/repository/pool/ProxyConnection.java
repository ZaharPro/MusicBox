package com.epam.musicbox.repository.pool;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ProxyConnection implements Connection {

    private final ConnectionPool pool;
    private final Connection core;

    ProxyConnection(ConnectionPool pool, Connection core) {
        this.pool = pool;
        this.core = core;
    }

    void closeConnection() throws SQLException {
        core.close();
    }

    @Override
    public void close() throws SQLException {
        if (!core.getAutoCommit()) {
            core.setAutoCommit(true);
        }
        pool.release(this);
    }

    @Override
    public Statement createStatement() throws SQLException {
        return core.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return core.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return core.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return core.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        core.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return core.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        core.commit();
    }

    @Override
    public void rollback() throws SQLException {
        core.rollback();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return core.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return core.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        core.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return core.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        core.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return core.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        core.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return core.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return core.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        core.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return core.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return core.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return core.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return core.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        core.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        core.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return core.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return core.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return core.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        core.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        core.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType,
                                     int resultSetConcurrency,
                                     int resultSetHoldability) throws SQLException {
        return core.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
                                              int resultSetType,
                                              int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        return core.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql,
                                         int resultSetType,
                                         int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        return core.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return core.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return core.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return core.prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException {
        return core.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return core.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return core.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return core.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return core.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        core.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        core.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return core.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return core.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return core.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return core.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        core.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return core.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        core.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        core.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return core.getNetworkTimeout();
    }

    public void beginRequest() throws SQLException {
        core.beginRequest();
    }

    public void endRequest() throws SQLException {
        core.endRequest();
    }

    @Override
    public boolean setShardingKeyIfValid(ShardingKey shardingKey, ShardingKey superShardingKey, int timeout) throws SQLException {
        return core.setShardingKeyIfValid(shardingKey, superShardingKey, timeout);
    }

    @Override
    public boolean setShardingKeyIfValid(ShardingKey shardingKey, int timeout) throws SQLException {
        return core.setShardingKeyIfValid(shardingKey, timeout);
    }

    @Override
    public void setShardingKey(ShardingKey shardingKey, ShardingKey superShardingKey) throws SQLException {
        core.setShardingKey(shardingKey, superShardingKey);
    }

    @Override
    public void setShardingKey(ShardingKey shardingKey) throws SQLException {
        core.setShardingKey(shardingKey);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return core.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return core.isWrapperFor(iface);
    }
}
