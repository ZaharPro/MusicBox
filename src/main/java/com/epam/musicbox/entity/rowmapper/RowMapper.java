package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T map(ResultSet resultSet) throws RepositoryException;
}
