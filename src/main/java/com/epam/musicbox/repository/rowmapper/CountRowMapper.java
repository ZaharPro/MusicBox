package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountRowMapper implements RowMapper<Long> {

    private static final String COUNT = "COUNT(*)";

    private static final CountRowMapper instance = new CountRowMapper();

    private CountRowMapper() {
    }

    public static CountRowMapper getInstance() {
        return instance;
    }

    @Override
    public Long map(ResultSet resultSet) throws RepositoryException {
        try {
            return resultSet.getLong(COUNT);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
