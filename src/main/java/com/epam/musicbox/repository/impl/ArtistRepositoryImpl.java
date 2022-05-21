package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class ArtistRepositoryImpl implements ArtistRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *\040
            FROM artists\040
            ORDER BY name\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_BY_ID = """
            SELECT *\040
            FROM artists\040
            WHERE id = ?""";

    private static final String SQL_INSERT_ONE = """
            INSERT INTO artists (name, avatar)\040
            VALUES (?, ?)""";

    private static final String SQL_UPDATE_ONE = """
            UPDATE artists (id, name, avatar)\040
            SET (?, ?, ?)""";

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM artists\040
            WHERE id = ?""";

    private static final String SQL_FIND_TRACKS = """
            SELECT *\040
            FROM tracks\040
            JOIN artist_liked_tracks\040
            ON artist_liked_tracks.track_id=tracks.track_id\040
            WHERE artist_liked_tracks.artist_id = ?\040
            LIMIT ?, ?""";

    @Override
    public List<Artist> findAll(int offset, int limit) throws HttpException {
        EntityBuilder<Artist> builder = new Artist.Builder();
        return QueryHelper.queryAll(SQL_FIND_ALL, builder, offset, limit);
    }

    @Override
    public Optional<Artist> findById(Integer id) throws HttpException {
        EntityBuilder<Artist> builder = new Artist.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_ID, builder, id);
    }

    @Override
    public void save(Artist artist) throws HttpException {
        Integer artistId = artist.getId();
        if (artistId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    artist.getName(),
                    artist.getAvatar());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    artist.getId(),
                    artist.getName(),
                    artist.getAvatar());
        }
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Track> getTracks(Integer artistId, int offset, int limit) throws HttpException {
        EntityBuilder<Track> builder = new Track.Builder();
        return QueryHelper.queryAll(SQL_FIND_TRACKS, builder, artistId, offset, limit);
    }
}
