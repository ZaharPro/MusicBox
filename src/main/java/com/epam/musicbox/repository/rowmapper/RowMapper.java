package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;

/**
 * The interface Row mapper.
 *
 * @param <T> the type parameter
 */
public interface RowMapper<T> {

    /**
     * Map t.
     *
     * @param resultSet the result set
     * @return the t
     * @throws RepositoryException the repository exception
     */
    T map(ResultSet resultSet) throws RepositoryException;
}
