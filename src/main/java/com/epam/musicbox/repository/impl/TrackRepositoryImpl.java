package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class TrackRepositoryImpl implements TrackRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *
            FROM tracks
            ORDER BY name
            LIMIT ?,?""";
    private static final String SQL_FIND_BY_ID = """
            SELECT *
            FROM tracks
            WHERE track_id=?""";
    private static final String SQL_INSERT_ONE = """
            INSERT INTO tracks (name, path)
            VALUES (?,?)""";
    private static final String SQL_UPDATE_ONE = """
            UPDATE tracks (name, path)
            SET name=? path=?
            WHERE track_id=?""";
    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM tracks
            WHERE track_id=?""";
    private static final String SQL_FIND_BY_NAME = """
            SELECT *
            FROM tracks
            WHERE name=?""";

    @Inject
    private Track.Builder trackBuilder;

    @Override
    public List<Track> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, trackBuilder, offset, limit);
    }

    @Override
    public Optional<Track> findById(Long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, trackBuilder, id);
    }

    @Override
    public void save(Track track) throws HttpException {
        Long trackId = track.getId();
        if (trackId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    track.getName(),
                    track.getPath());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    track.getName(),
                    track.getPath(),
                    track.getId());
        }
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<Track> findByName(String name) {
        return QueryHelper.queryOne(SQL_FIND_BY_NAME, trackBuilder, name);
    }
}
