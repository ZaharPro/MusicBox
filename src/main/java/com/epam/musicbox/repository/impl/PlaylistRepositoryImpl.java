package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.rowmapper.BooleanRowMapper;
import com.epam.musicbox.repository.rowmapper.CountRowMapper;
import com.epam.musicbox.repository.rowmapper.PlaylistRowMapper;
import com.epam.musicbox.repository.rowmapper.TrackRowMapper;

import java.util.List;
import java.util.Optional;

public class PlaylistRepositoryImpl implements PlaylistRepository {

    private static final String SQL_COUNT = "SELECT COUNT(p.playlist_id) " +
                                            "FROM playlists AS p";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM playlists AS p " +
                                               "ORDER BY p.name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM playlists AS p " +
                                                 "WHERE p.playlist_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO playlists (name, picture, user_id) " +
                                                 "VALUES (?,?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE playlists " +
                                                 "SET name=?, picture=?, user_id=? " +
                                                 "WHERE playlist_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM playlists " +
                                                   "WHERE playlist_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(p.playlist_id) " +
                                                    "FROM playlists AS p " +
                                                    "WHERE p.name REGEXP (?)";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM playlists AS p " +
                                                   "WHERE p.name REGEXP (?) " +
                                                   "ORDER BY p.name " +
                                                   "LIMIT ?,?";

    private static final String SQL_COUNT_TRACKS = "SELECT COUNT(t.track_id) " +
                                                   "FROM tracks AS t " +
                                                   "JOIN playlist_tracks AS pt " +
                                                   "ON pt.track_id = t.track_id " +
                                                   "WHERE pt.playlist_id=?";

    private static final String SQL_FIND_TRACKS = "SELECT * " +
                                                  "FROM tracks AS t " +
                                                  "JOIN playlist_tracks AS pt " +
                                                  "ON pt.track_id = t.track_id " +
                                                  "WHERE pt.playlist_id=? " +
                                                  "ORDER BY t.name " +
                                                  "LIMIT ?,?";

    private static final String SQL_EXIST_TRACK = "SELECT 1 " +
                                                  "FROM playlist_tracks AS pt " +
                                                  "WHERE pt.playlist_id=? AND pt.track_id=?";

    private static final String SQL_ADD_TRACK = "INSERT INTO playlist_tracks (playlist_id, track_id) " +
                                                "VALUES (?,?)";

    private static final String SQL_REMOVE_TRACK = "DELETE FROM playlist_tracks " +
                                                   "WHERE playlist_id=? AND track_id=?";

    public static final PlaylistRepositoryImpl instance = new PlaylistRepositoryImpl();

    private final TrackRowMapper trackRowMapper = TrackRowMapper.getInstance();
    private final PlaylistRowMapper playlistRowMapper = PlaylistRowMapper.getInstance();
    private final CountRowMapper countRowMapper = CountRowMapper.getInstance();
    private final BooleanRowMapper booleanRowMapper = BooleanRowMapper.getInstance();

    private PlaylistRepositoryImpl() {
    }

    public static PlaylistRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT, countRowMapper).orElse(0L);
    }

    @Override
    public List<Playlist> findAll(int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_ALL, playlistRowMapper, offset, limit);
    }

    @Override
    public Optional<Playlist> findById(long id) throws RepositoryException {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, playlistRowMapper, id);
    }

    @Override
    public long save(Playlist playlist) throws RepositoryException {
        Long playlistId = playlist.getId();
        if (playlistId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    playlist.getName(),
                    playlist.getPicture(),
                    playlist.getUserId());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    playlist.getName(),
                    playlist.getPicture(),
                    playlist.getUserId(),
                    playlistId);
            return playlistId;
        }
    }

    @Override
    public void deleteById(long id) throws RepositoryException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public long countByName(String regex) throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT_BY_NAME, countRowMapper, regex).orElse(0L);
    }

    @Override
    public List<Playlist> findByName(String regex, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, playlistRowMapper, regex, offset, limit);
    }

    @Override
    public long countTracks(long artistId) throws RepositoryException {
        return QueryHelper.queryOne(SQL_COUNT_TRACKS, countRowMapper, artistId).orElse(0L);
    }

    @Override
    public List<Track> getTracks(long playlistId, int offset, int limit) throws RepositoryException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackRowMapper, playlistId, offset, limit);
    }

    @Override
    public boolean hasTrack(long artistId, long trackId) throws RepositoryException {
        return QueryHelper.queryOne(SQL_EXIST_TRACK, booleanRowMapper, artistId, trackId).orElse(false);
    }

    @Override
    public void addTrack(long playlistId, long trackId) throws RepositoryException {
        if (!hasTrack(playlistId, trackId)) {
            QueryHelper.update(SQL_ADD_TRACK, playlistId, trackId);
        }
    }

    @Override
    public void removeTrack(long playlistId, long trackId) throws RepositoryException {
        QueryHelper.update(SQL_REMOVE_TRACK, playlistId, trackId);
    }
}
