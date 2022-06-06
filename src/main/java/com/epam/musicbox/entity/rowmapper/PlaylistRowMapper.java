package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistRowMapper implements RowMapper<Playlist> {
    private static final PlaylistRowMapper instance = new PlaylistRowMapper();

    private PlaylistRowMapper() {
    }

    public static PlaylistRowMapper getInstance() {
        return instance;
    }

    @Override
    public Playlist map(ResultSet resultSet) throws RepositoryException {
        try {
            return new Playlist(resultSet.getLong("playlist_id"),
                    resultSet.getString("name"),
                    resultSet.getString("picture"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}