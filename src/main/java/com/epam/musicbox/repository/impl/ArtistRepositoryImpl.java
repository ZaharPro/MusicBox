package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ArtistRepositoryImpl implements ArtistRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *
            FROM artists
            ORDER BY name
            LIMIT ?,?""";

    private static final String SQL_FIND_BY_ID = """
            SELECT *
            FROM artists
            WHERE artist_id=?""";

    private static final String SQL_INSERT_ONE = """
            INSERT INTO artists (name, avatar)
            VALUES (?,?)""";

    private static final String SQL_UPDATE_ONE = """
            UPDATE artists (name, avatar)
            SET name=? avatar=?
            WHERE artist_id=?""";

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM artists
            WHERE artist_id=?""";

    private static final String SQL_FIND_BY_NAME = """
            SELECT *
            FROM artists
            WHERE name=?""";

    private static final String SQL_FIND_TRACKS = """
            SELECT *
            FROM tracks
            JOIN artist_tracks
            ON artist_tracks.track_id = tracks.track_id
            WHERE artist_tracks.artist_id=?""";

    private static final String SQL_ADD_TRACK = """
            INSERT INTO artist_tracks (artist_id, track_id)
            VALUES (?,?)""";

    private static final String SQL_REMOVE_TRACK =  """
            DELETE FROM artist_tracks
            WHERE artist_id=? AND artist_id=?""";

    @Inject
    private Track.Builder trackEntityBuilder;

    @Inject
    private Artist.Builder artistEntityBuilder;

    @Override
    public List<Artist> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, artistEntityBuilder, offset, limit);
    }

    @Override
    public Optional<Artist> findById(Long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, artistEntityBuilder, id);
    }

    @Override
    public void save(Artist artist) throws HttpException {
        Long artistId = artist.getId();
        if (artistId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    artist.getName(),
                    artist.getAvatar());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    artist.getName(),
                    artist.getAvatar(),
                    artist.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<Artist> findByName(String name) {
        return QueryHelper.queryOne(SQL_FIND_BY_NAME, artistEntityBuilder, name);
    }

    @Override
    public List<Track> getTracks(Long artistId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackEntityBuilder, artistId, offset, limit);
    }

    @Override
    public void addTrack(Long artistId, Long trackId) throws HttpException {
        QueryHelper.update(SQL_ADD_TRACK, artistId, trackId);
    }

    @Override
    public void removeTrack(Long artistId, Long trackId) throws HttpException {
        QueryHelper.update(SQL_REMOVE_TRACK, artistId, trackId);
    }
}
