package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type User row mapper.
 */
public class UserRowMapper implements RowMapper<User> {

    private static final String ROW_USER_ID = "user_id";
    private static final String ROW_LOGIN = "login";
    private static final String ROW_EMAIL = "email";
    private static final String ROW_PASSWORD = "password";
    private static final String ROW_ROLE_ID = "role_id";
    private static final String ROW_BANNED = "banned";
    private static final String ROW_REGISTRATION = "registration";

    private static final UserRowMapper instance = new UserRowMapper();

    private UserRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
                    findById(resultSet.getInt(ROW_ROLE_ID)),
                    resultSet.getBoolean(ROW_BANNED),
                    resultSet.getTimestamp(ROW_REGISTRATION));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    private static Role findById(int id) {
        for (Role role : Role.values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        return null;
    }
}