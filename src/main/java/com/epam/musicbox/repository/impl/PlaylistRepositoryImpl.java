package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.EntityBuilder;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.util.QueryHelper;

import java.util.List;
import java.util.Optional;

public class PlaylistRepositoryImpl implements PlaylistRepository {
    private static final String SQL_FIND_ALL = """
            SELECT *\040
            FROM playlists\040
            LIMIT ?, ?""";

    private static final String SQL_FIND_BY_ID = """
            SELECT *\040
            FROM playlists\040
            WHERE id = ?""";

    private static final String SQL_INSERT_ONE = """
            INSERT INTO playlists""";

    private static final String SQL_UPDATE_ONE = """
            UPDATE playlists (id)\040
            SET (?)""";

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM playlists\040
            WHERE id = ?""";

    private static final String SQL_FIND_TRACKS = """
            SELECT *\040
            FROM tracks\040
            JOIN playlist_tracks\040
            ON playlist_tracks.track_id=tracks.track_id\040
            WHERE playlist_tracks.playlist_id = ?\040
            LIMIT ?, ?""";

    @Override
    public List<Playlist> findAll(int offset, int limit) throws HttpException {
        EntityBuilder<Playlist> builder = new Playlist.Builder();
        return QueryHelper.queryAll(SQL_FIND_ALL, builder, offset, limit);
    }

    @Override
    public Optional<Playlist> findById(Integer id) throws HttpException {
        EntityBuilder<Playlist> builder = new Playlist.Builder();
        return QueryHelper.queryOne(SQL_FIND_BY_ID, builder, id);
    }

    @Override
    public void save(Playlist playlist) throws HttpException {
        Integer playlistId = playlist.getId();
        if (playlistId == null) {
            QueryHelper.update(SQL_INSERT_ONE);
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    playlist.getId());
        }
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Track> getTracks(Integer playlistId, int offset, int limit) throws HttpException {
        EntityBuilder<Track> builder = new Track.Builder();
        return QueryHelper.queryAll(SQL_FIND_TRACKS, builder, playlistId, offset, limit);
    }
}
