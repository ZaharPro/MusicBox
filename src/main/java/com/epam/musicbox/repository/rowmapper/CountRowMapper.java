package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Count row mapper.
 */
public class CountRowMapper implements RowMapper<Long> {

    private static final int COUNT_COL_INDEX = 1;

    private static final CountRowMapper instance = new CountRowMapper();

    private CountRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CountRowMapper getInstance() {
        return instance;
    }

    @Override
    public Long map(ResultSet resultSet) throws RepositoryException {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getLong(COUNT_COL_INDEX);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
