package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.repository.rowmapper.ArtistRowMapper;
import com.epam.musicbox.repository.rowmapper.CountRowMapper;
import com.epam.musicbox.repository.rowmapper.TrackRowMapper;

import java.util.List;
import java.util.Optional;

public class TrackRepositoryImpl implements TrackRepository {

    private static final String SQL_COUNT = "SELECT COUNT(t.track_id) " +
                                            "FROM tracks AS t";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM tracks AS t " +
                                               "ORDER BY t.name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM tracks AS t " +
                                                 "WHERE t.track_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO tracks (name, audio, album_id) " +
                                                 "VALUES (?,?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE tracks " +
                                                 "SET name=?, audio=?, album_id=? " +
                                                 "WHERE track_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM tracks " +
                                                   "WHERE track_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(t.track_id) " +
                                                    "FROM tracks AS t " +
                                                    "WHERE t.name LIKE CONCAT('%',?,'%')";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM tracks AS t " +
                                                   "WHERE t.name LIKE CONCAT('%',?,'%') " +
                                                   "ORDER BY t.name " +
                                                   "LIMIT ?,?";

    private static final String SQL_COUNT_ARTISTS = "SELECT COUNT(a.artist_id) " +
                                                    "FROM artists AS a " +
                                                    "JOIN artist_tracks AS at " +
                                                    "ON at.artist_id = a.artist_id " +
                                                    "WHERE at.track_id=?";

    private static final String SQL_FIND_ARTISTS = "SELECT * " +
                                                   "FROM artists AS a " +
                                                   "JOIN artist_tracks AS at " +
                                                   "ON at.artist_id = a.artist_id " +
                                                   "WHERE at.track_id=? " +
                                                   "ORDER BY a.name " +
                                                   "LIMIT ?,?";

    public static final TrackRepositoryImpl instance = new TrackRepositoryImpl();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();
    private final ArtistRowMapper artistRowMapper = ArtistRowMapper.getInstance();
    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();

    private TrackRepositoryImpl() {
    }

    public static TrackRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT, countRowMapper).orElse(0L);
    }

    @Override
    public List<Track> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, trackRowMapper, offset, limit);
    }

    @Override
    public Optional<Track> findById(long id) throws RepositoryException {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, trackRowMapper, id);
    }

    @Override
    public long save(Track track) throws RepositoryException {
        Long trackId = track.getId();
        if (trackId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    track.getName(),
                    track.getAudio(),
                    track.getAlbumId());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    track.getName(),
                    track.getAudio(),
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
    public long countByName(String name) throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT_BY_NAME, countRowMapper, name).orElse(0L);
    }

    @Override
    public List<Track> findByName(String name, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, trackRowMapper, name, offset, limit);
    }

    @Override
    public long countArtists(long trackId) throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT_ARTISTS, countRowMapper, trackId).orElse(0L);
    }

    @Override
    public List<Artist> getArtists(long trackId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ARTISTS, artistRowMapper, trackId, offset, limit);
    }
}
