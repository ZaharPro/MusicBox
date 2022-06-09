package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T map(ResultSet resultSet) throws RepositoryException;
}
