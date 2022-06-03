package com.epam.musicbox.entity;

import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;

import java.sql.ResultSet;

public interface EntityBuilder<T> {
    T build(ResultSet resultSet) throws RepositoryException;
}
