package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackRowMapper implements RowMapper<Track> {
    private static final TrackRowMapper instance = new TrackRowMapper();

    private TrackRowMapper() {
    }

    public static TrackRowMapper getInstance() {
        return instance;
    }

    @Override
    public Track map(ResultSet resultSet) throws RepositoryException {
        try {
            return new Track(resultSet.getLong("track_id"),
                    resultSet.getString("name"),
                    resultSet.getString("path"),
                    resultSet.getLong("album_id"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}