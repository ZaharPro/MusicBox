package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {
    private static final AlbumRowMapper instance = new AlbumRowMapper();

    private AlbumRowMapper() {
    }

    public static AlbumRowMapper getInstance() {
        return instance;
    }

    @Override
    public Album map(ResultSet resultSet) throws RepositoryException {
        try {
            return new Album(resultSet.getLong("album_id"),
                    resultSet.getString("name"),
                    resultSet.getString("picture"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}