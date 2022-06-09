package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistRowMapper implements RowMapper<Playlist> {

    public static final String ROW_PLAYLIST_ID = "playlist_id";
    public static final String ROW_NAME = "name";
    public static final String ROW_PICTURE = "picture";

    private static final PlaylistRowMapper instance = new PlaylistRowMapper();

    private PlaylistRowMapper() {
    }

    public static PlaylistRowMapper getInstance() {
        return instance;
    }

    @Override
    public Playlist map(ResultSet resultSet) throws RepositoryException {
        try {
            return new Playlist(resultSet.getLong(ROW_PLAYLIST_ID),
                    resultSet.getString(ROW_NAME),
                    resultSet.getString(ROW_PICTURE));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}