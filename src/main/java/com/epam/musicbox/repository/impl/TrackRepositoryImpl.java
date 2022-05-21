package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.EntityBuilder;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class TrackRepositoryImpl implements TrackRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *\040
            FROM tracks\040
            ORDER BY name\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_BY_ID = """
            SELECT *\040
            FROM tracks\040
            WHERE id = ?""";

    private static final String SQL_INSERT_ONE = """
            INSERT INTO tracks (name, path, album_id)\040
            VALUES (?, ?, ?)""";

    private static final String SQL_UPDATE_ONE = """
            UPDATE tracks (id, name, path, album_id)\040
            SET (?, ?, ?, ?)""";

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM tracks\040
            WHERE id = ?""";

    @Override
    public List<Track> findAll(int offset, int limit) throws HttpException {
        EntityBuilder<Track> builder = new Track.Builder();
        return QueryHelper.queryAll(SQL_FIND_ALL, builder, offset, limit);
    }

    @Override
    public Optional<Track> findById(Integer id) throws HttpException {
        EntityBuilder<Track> builder = new Track.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_ID, builder, id);
    }

    @Override
    public void save(Track track) throws HttpException {
        Integer trackId = track.getId();
        if (trackId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    track.getName(),
                    track.getPath());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    track.getId(),
                    track.getName(),
                    track.getPath());
        }
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }
}
