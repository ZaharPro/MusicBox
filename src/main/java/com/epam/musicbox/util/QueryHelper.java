package com.epam.musicbox.util;

import com.epam.musicbox.database.ConnectionPool;
import com.epam.musicbox.entity.EntityBuilder;
import com.epam.musicbox.exception.HttpException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class QueryHelper {
    public static final Logger logger = LogManager.getLogger();

    private QueryHelper() {
    }

    public static <T> Optional<T> queryOne(String sql,
                                           EntityBuilder<T> builder,
                                           Object... params) {
        try {
            List<T> items = QueryHelper.queryAll(sql, builder, params);
            return items.size() == 1 ?
                    Optional.of(items.get(0)) :
                    Optional.empty();
        } catch (HttpException e) {
            logger.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static <T> List<T> queryAll(String sql,
                                       EntityBuilder<T> builder,
                                       Object... params) throws HttpException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, params);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = builder.build(resultSet);
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            throw new HttpException(e);
        }
    }

    public static long insert(String sql, Object... params) throws HttpException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, params);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new HttpException(e);
        }
    }

    public static void update(String sql, Object... params) throws HttpException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, params);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new HttpException(e);
        }
    }

    public static void prepare(PreparedStatement preparedStatement, Object... params) throws SQLException {
        int i = 1;
        for (Object param : params) {
            Class<?> paramClass = param.getClass();
            if (paramClass == Integer.class) {
                preparedStatement.setInt(i, (Integer) param);
            } else if (paramClass == Timestamp.class) {
                preparedStatement.setTimestamp(i, (Timestamp) param);
            } else {
                preparedStatement.setString(i, String.valueOf(param));
            }
            i++;
        }
    }
}
