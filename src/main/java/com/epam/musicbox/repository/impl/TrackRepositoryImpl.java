package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.rowmapper.TrackRowMapper;
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

    private static final String SQL_INSERT_ONE = "INSERT INTO tracks (name, path, album_id) " +
                                                 "VALUES (?,?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE tracks " +
                                                 "SET name=? path=? album_id=? " +
                                                 "WHERE track_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM tracks " +
                                                   "WHERE track_id=?";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM tracks " +
                                                   "WHERE name REGEXP (?) " +
                                                   "ORDER BY name " +
                                                   "LIMIT ?,?";

    public static final TrackRepositoryImpl instance = new TrackRepositoryImpl();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();

    public static TrackRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public List<Track> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, trackRowMapper, offset, limit);
    }

    @Override
    public Optional<Track> findById(long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, trackRowMapper, id);
    }

    @Override
    public long save(Track track) throws RepositoryException {
        Long trackId = track.getId();
        if (trackId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    track.getName(),
                    track.getPath(),
                    track.getAlbumId());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    track.getName(),
                    track.getPath(),
                    track.getAlbumId(),
                    trackId);
            return trackId;
        }
    }

    @Override
    public void deleteById(long id) throws RepositoryException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Track> findByName(String regex, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, trackRowMapper, regex, offset, limit);
    }
}
