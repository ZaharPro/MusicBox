package com.epam.musicbox.repository.rowmapper;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Artist row mapper.
 */
public class ArtistRowMapper implements RowMapper<Artist> {

    private static final String ROW_ARTIST_ID = "artist_id";
    private static final String ROW_NAME = "name";
    private static final String ROW_AVATAR = "avatar";

    private static final ArtistRowMapper instance = new ArtistRowMapper();

    private ArtistRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ArtistRowMapper getInstance() {
        return instance;
    }

    @Override
    public Artist map(ResultSet resultSet) throws RepositoryException {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return new Artist(resultSet.getLong(ROW_ARTIST_ID),
                    resultSet.getString(ROW_NAME),
                    resultSet.getString(ROW_AVATAR));
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}