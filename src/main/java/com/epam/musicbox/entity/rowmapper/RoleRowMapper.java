package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    private static final RoleRowMapper instance = new RoleRowMapper();

    private RoleRowMapper() {
    }

    public static RoleRowMapper getInstance() {
        return instance;
    }

    @Override
    public Role map(ResultSet resultSet) throws RepositoryException {
        try {
            int roleId = resultSet.getInt("role_id");
            return Role.findById(roleId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}