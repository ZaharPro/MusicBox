package com.epam.musicbox.util;

import com.epam.musicbox.database.ConnectionPool;
import com.epam.musicbox.entity.EntityBuilder;
import com.epam.musicbox.exception.HttpException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryHelper {
    public static <T> Optional<T> queryOne(String sql,
                                           EntityBuilder<T> builder,
                                           Object... params) throws HttpException {
        List<T> items = QueryHelper.queryAll(sql, builder, params);
        return items.size() == 1 ?
                Optional.of(items.get(0)) :
                Optional.empty();
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
            throw new HttpException(e.getMessage(), e);
        }
    }

    public static void update(String sql, Object... fields) throws HttpException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            QueryHelper.prepare(preparedStatement, fields);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new HttpException(e.getMessage(), e);
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
