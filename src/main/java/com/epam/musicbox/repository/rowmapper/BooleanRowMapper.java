package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanRowMapper implements RowMapper<Boolean> {

    private static final int BOOLEAN_COL_INDEX = 1;

    private static final BooleanRowMapper instance = new BooleanRowMapper();

    private BooleanRowMapper() {
    }

    public static BooleanRowMapper getInstance() {
        return instance;
    }

    @Override
    public Boolean map(ResultSet resultSet) throws RepositoryException {
        try {
            return resultSet.getBoolean(BOOLEAN_COL_INDEX);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
