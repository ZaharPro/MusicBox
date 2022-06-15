package com.epam.musicbox.util;

import com.epam.musicbox.repository.pool.ConnectionPool;
import com.epam.musicbox.repository.rowmapper.CountRowMapper;
import com.epam.musicbox.repository.rowmapper.RowMapper;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class QueryHelper {

    private static final String SQL_COUNT = "SELECT COUNT(*) FROM ";
    private static final String SQL_WHERE = "WHERE ";

    private QueryHelper() {
    }

    public static <T> Optional<T> queryOne(String sql,
                                           RowMapper<T> builder,
                                           Object... params) throws RepositoryException {
        List<T> items = QueryHelper.queryAll(sql, builder, params);
        return items.size() == 1 ?
                Optional.of(items.get(0)) :
                Optional.empty();
    }

    public static <T> List<T> queryAll(String sql,
                                       RowMapper<T> builder,
                                       Object... params) throws RepositoryException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = builder.map(resultSet);
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public static long insert(String sql, Object... params) throws RepositoryException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            QueryHelper.prepare(preparedStatement, params);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public static void update(String sql, Object... params) throws RepositoryException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, params);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    public static void prepare(PreparedStatement statement, Object... params) throws SQLException {
        int i = 1;
        for (Object param : params) {
            if (param == null) {
                statement.setObject(i, "null");
            } else {
                Class<?> paramClass = param.getClass();
                if (paramClass == Integer.class) {
                    statement.setInt(i, (Integer) param);
                } else if (paramClass == Boolean.class) {
                    statement.setBoolean(i, (boolean) param);
                } else if (paramClass == Timestamp.class) {
                    statement.setTimestamp(i, (Timestamp) param);
                } else {
                    statement.setString(i, String.valueOf(param));
                }
            }
            i++;
        }
    }

    public static long count(String table, String condition, Object... params) throws RepositoryException {
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_COUNT).append(table);
        if (condition != null) {
            sb.append(SQL_WHERE).append(condition);
        }
        String sql = sb.toString();
        Optional<Long> optionalCount = QueryHelper.queryOne(sql, CountRowMapper.getInstance(), params);
        return optionalCount.orElse(0L);
    }

    public static long count(String table) throws RepositoryException {
        return count(table, null);
    }

    public static boolean exist(String table, String condition, Object... params) throws RepositoryException {
        return count(table, condition, params) != 0;
    }
}
