package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public static final String ROW_USER_ID = "user_id";
    public static final String ROW_LOGIN = "login";
    public static final String ROW_EMAIL = "email";
    public static final String ROW_PASSWORD = "password";
    public static final String ROW_BANNED = "banned";
    public static final String ROW_REGISTRATION = "registration";

    private static final UserRowMapper instance = new UserRowMapper();

    private UserRowMapper() {
    }

    public static UserRowMapper getInstance() {
        return instance;
    }

    @Override
    public User map(ResultSet resultSet) throws RepositoryException {
        try {
            return new User(resultSet.getLong(ROW_USER_ID),
                    resultSet.getString(ROW_LOGIN),
                    resultSet.getString(ROW_EMAIL),
                    resultSet.getString(ROW_PASSWORD),
                    resultSet.getBoolean(ROW_BANNED),
                    resultSet.getTimestamp(ROW_REGISTRATION));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}