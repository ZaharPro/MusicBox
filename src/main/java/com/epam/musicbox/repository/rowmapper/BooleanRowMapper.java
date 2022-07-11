package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Boolean row mapper.
 */
public class BooleanRowMapper implements RowMapper<Boolean> {

    private static final int BOOLEAN_COL_INDEX = 1;

    private static final BooleanRowMapper instance = new BooleanRowMapper();

    private BooleanRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BooleanRowMapper getInstance() {
        return instance;
    }

    @Override
    public Boolean map(ResultSet resultSet) throws RepositoryException {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getBoolean(BOOLEAN_COL_INDEX);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
