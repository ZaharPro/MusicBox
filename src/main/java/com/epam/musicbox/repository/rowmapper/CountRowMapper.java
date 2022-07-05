package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountRowMapper implements RowMapper<Long> {

    private static final int COUNT_COL_INDEX = 1;

    private static final CountRowMapper instance = new CountRowMapper();

    private CountRowMapper() {
    }

    public static CountRowMapper getInstance() {
        return instance;
    }

    @Override
    public Long map(ResultSet resultSet) throws RepositoryException {
        try {
            return resultSet.getLong(COUNT_COL_INDEX);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
