package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    private static final String ROW_ROLE_ID = "role_id";

    private static final RoleRowMapper instance = new RoleRowMapper();

    private RoleRowMapper() {
    }

    public static RoleRowMapper getInstance() {
        return instance;
    }

    @Override
    public Role map(ResultSet resultSet) throws RepositoryException {
        try {
            int roleId = resultSet.getInt(ROW_ROLE_ID);
            return Role.findById(roleId);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}