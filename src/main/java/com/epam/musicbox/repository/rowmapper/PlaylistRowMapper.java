package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistRowMapper implements RowMapper<Playlist> {

    private static final String ROW_PLAYLIST_ID = "playlist_id";
    private static final String ROW_NAME = "name";
    private static final String ROW_PICTURE = "picture";
    private static final String ROW_USER_ID = "user_id";

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
                    resultSet.getString(ROW_PICTURE),
                    resultSet.getLong(ROW_USER_ID));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}