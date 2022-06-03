package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class PlaylistRepositoryImpl implements PlaylistRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *
            FROM playlists
            ORDER BY name
            LIMIT ?,?""";

    private static final String SQL_FIND_BY_ID = """
            SELECT *
            FROM playlists
            WHERE playlist_id=?""";

    private static final String SQL_INSERT_ONE = """
            INSERT INTO playlists (name)
            VALUES (?)""";

    private static final String SQL_UPDATE_ONE = """
            UPDATE playlists (name)
            SET name=?
            WHERE playlist_id=?""";

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM playlists
            WHERE playlist_id=?""";

    private static final String SQL_FIND_BY_NAME = """
            SELECT *
            FROM playlists
            WHERE name REGEXP (?)
            ORDER BY name
            LIMIT ?,?""";

    private static final String SQL_FIND_TRACKS = """
            SELECT *
            FROM tracks
            JOIN playlist_tracks
            ON playlist_tracks.track_id = tracks.track_id
            WHERE playlist_tracks.playlist_id=?""";

    private static final String SQL_ADD_TRACK = """
            INSERT INTO playlist_tracks (playlist_id, track_id)
            VALUES (?,?)""";

    private static final String SQL_REMOVE_TRACK =  """
            DELETE FROM playlist_tracks
            WHERE playlist_id=? AND track_id=?""";

    public static final PlaylistRepositoryImpl instance = new PlaylistRepositoryImpl();

    private final Track.Builder trackEntityBuilder = Track.Builder.getInstance();

    private final Playlist.Builder playlistEntityBuilder = Playlist.Builder.getInstance();

    public static PlaylistRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public List<Playlist> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, playlistEntityBuilder, offset, limit);
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, playlistEntityBuilder, id);
    }

    @Override
    public long save(Playlist playlist) throws HttpException {
        Long playlistId = playlist.getId();
        if (playlistId == null) {
            return QueryHelper.insert(SQL_INSERT_ONE,
                    playlist.getName());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    playlist.getName(),
                    playlistId);
            return playlistId;
        }
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Playlist> findByName(String regex, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_BY_NAME, playlistEntityBuilder, regex, offset, limit);
    }

    @Override
    public List<Track> getTracks(Long playlistId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackEntityBuilder, playlistId, offset, limit);
    }

    @Override
    public void addTrack(Long playlistId, Long trackId) throws HttpException {
        QueryHelper.update(SQL_ADD_TRACK, playlistId, trackId);
    }

    @Override
    public void removeTrack(Long playlistId, Long trackId) throws HttpException {
        QueryHelper.update(SQL_REMOVE_TRACK, playlistId, trackId);
    }
}
