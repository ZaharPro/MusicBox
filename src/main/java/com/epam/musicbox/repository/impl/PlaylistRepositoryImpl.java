package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.rowmapper.BooleanRowMapper;
import com.epam.musicbox.repository.rowmapper.CountRowMapper;
import com.epam.musicbox.repository.rowmapper.PlaylistRowMapper;
import com.epam.musicbox.repository.rowmapper.TrackRowMapper;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class PlaylistRepositoryImpl implements PlaylistRepository {

    private static final String SQL_COUNT = "SELECT COUNT(*) " +
                                            "FROM artists";

    private static final String SQL_FIND_ALL = "SELECT * " +
                                               "FROM playlists " +
                                               "ORDER BY name " +
                                               "LIMIT ?,?";

    private static final String SQL_FIND_BY_ID = "SELECT * " +
                                                 "FROM playlists " +
                                                 "WHERE playlist_id=?";

    private static final String SQL_INSERT_ONE = "INSERT INTO playlists (name, picture) " +
                                                 "VALUES (?,?)";

    private static final String SQL_UPDATE_ONE = "UPDATE playlists " +
                                                 "SET name=?, picture=? " +
                                                 "WHERE playlist_id=?";

    private static final String SQL_DELETE_BY_ID = "DELETE FROM playlists " +
                                                   "WHERE playlist_id=?";

    private static final String SQL_COUNT_BY_NAME = "SELECT COUNT(*) " +
                                                    "FROM playlists " +
                                                    "WHERE name REGEXP (?)";

    private static final String SQL_FIND_BY_NAME = "SELECT * " +
                                                   "FROM playlists " +
                                                   "WHERE name REGEXP (?) " +
                                                   "ORDER BY name " +
                                                   "LIMIT ?,?";

    private static final String SQL_COUNT_TRACKS = "SELECT COUNT(*) " +
                                                   "FROM tracks " +
                                                   "JOIN playlist_tracks " +
                                                   "ON playlist_tracks.track_id = tracks.track_id " +
                                                   "WHERE playlist_tracks.playlist_id=?";

    private static final String SQL_FIND_TRACKS = "SELECT * " +
                                                  "FROM tracks " +
                                                  "JOIN playlist_tracks " +
                                                  "ON playlist_tracks.track_id = tracks.track_id " +
                                                  "WHERE playlist_tracks.playlist_id=? " +
                                                  "LIMIT ?,?";

    private static final String SQL_EXIST_TRACK = "SELECT 1 " +
                                                  "FROM playlist_tracks " +
                                                  "WHERE playlist_id=? AND track_id=?";

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
                    playlist.getPicture());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    playlist.getName(),
                    playlist.getPicture(),
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
