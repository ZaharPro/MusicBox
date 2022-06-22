package com.epam.musicbox.util;

import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.pool.ConnectionPool;
import com.epam.musicbox.repository.rowmapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class QueryHelper {

    private QueryHelper() {
    }

    public static <T> Optional<T> queryOne(String sql,
                                           RowMapper<T> mapper,
                                           Object... params) throws RepositoryException {
        List<T> items = QueryHelper.queryAll(sql, mapper, params);
        return items.size() == 1 ?
                Optional.ofNullable(items.get(0)) :
                Optional.empty();
    }

    public static <T> List<T> queryAll(String sql,
                                       RowMapper<T> mapper,
                                       Object... params) throws RepositoryException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = mapper.map(resultSet);
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
                statement.setObject(i, null);
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
}
