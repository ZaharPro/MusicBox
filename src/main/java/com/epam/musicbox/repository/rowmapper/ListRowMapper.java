package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ListRowMapper<T> implements RowMapper<List<T>> {

    private final RowMapper<T> mapper;

    public ListRowMapper(RowMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<T> map(ResultSet resultSet) throws RepositoryException {
        List<T> list = new ArrayList<>();
        T t;
        while ((t = mapper.map(resultSet)) != null) {
            list.add(t);
        }
        return list;
    }
}
