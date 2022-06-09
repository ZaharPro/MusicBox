package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {

    public static final String ROW_ALBUM_ID = "album_id";
    public static final String ROW_NAME = "name";
    public static final String ROW_PICTURE = "picture";

    private static final AlbumRowMapper instance = new AlbumRowMapper();

    private AlbumRowMapper() {
    }

    public static AlbumRowMapper getInstance() {
        return instance;
    }

    @Override
    public Album map(ResultSet resultSet) throws RepositoryException {
        try {
            return new Album(resultSet.getLong(ROW_ALBUM_ID),
                    resultSet.getString(ROW_NAME),
                    resultSet.getString(ROW_PICTURE));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}