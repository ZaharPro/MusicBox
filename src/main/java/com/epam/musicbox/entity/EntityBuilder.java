package com.epam.musicbox.entity;

import com.epam.musicbox.exception.HttpException;

import java.sql.ResultSet;

public interface EntityBuilder<T> {
    T build(ResultSet resultSet) throws HttpException;
}
