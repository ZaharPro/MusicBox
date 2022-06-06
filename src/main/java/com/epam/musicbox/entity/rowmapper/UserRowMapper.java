package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    private static final UserRowMapper instance = new UserRowMapper();

    private UserRowMapper() {
    }

    public static UserRowMapper getInstance() {
        return instance;
    }

    @Override
    public User map(ResultSet resultSet) throws RepositoryException {
        try {
            return new User(resultSet.getLong("user_id"),
                    resultSet.getString("login"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getBoolean("banned"),
                    resultSet.getTimestamp("registration"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}