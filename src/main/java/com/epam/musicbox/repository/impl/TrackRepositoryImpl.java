package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class TrackRepositoryImpl implements TrackRepository {
    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM tracks " +
                                               "ORDER BY name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM tracks " +
                                                 "WHERE track_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO tracks (name, path) " +
                                                 "VALUES (?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE tracks (name, path) " +
                                                 "SET name=? path=? " +
                                                 "WHERE track_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM tracks " +
                                                   "WHERE track_id=?";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM tracks " +
                                                   "WHERE name REGEXP (?) " +
                                                   "ORDER BY name " +
                                                   "LIMIT ?,?";

    public static final TrackRepositoryImpl instance = new TrackRepositoryImpl();

    private final Track.Builder trackEntityBuilder = Track.Builder.getInstance();

    public static TrackRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public List<Track> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, trackEntityBuilder, offset, limit);
    }

    @Override
    public Optional<Track> findById(Long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, trackEntityBuilder, id);
    }

    @Override
    public long save(Track track) throws RepositoryException {
        Long trackId = track.getId();
        if (trackId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    track.getName(),
                    track.getPath());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    track.getName(),
                    track.getPath(),
                    trackId);
            return trackId;
        }
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Track> findByName(String regex, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, trackEntityBuilder, regex, offset, limit);
    }
}
