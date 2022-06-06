package com.epam.musicbox.entity.rowmapper;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistRowMapper implements RowMapper<Artist> {
    private static final ArtistRowMapper instance = new ArtistRowMapper();

    private ArtistRowMapper() {
    }

    public static ArtistRowMapper getInstance() {
        return instance;
    }

    @Override
    public Artist map(ResultSet resultSet) throws RepositoryException {
        try {
            return new Artist(resultSet.getLong("artist_id"),
                    resultSet.getString("name"),
                    resultSet.getString("avatar"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}