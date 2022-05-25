package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class PlaylistRepositoryImpl implements PlaylistRepository {
    private static final String SQL_FIND_ALL = ;
    private static final String SQL_FIND_BY_ID = ;
    private static final String SQL_INSERT_ONE = ;
    private static final String SQL_DELETE_BY_ID = ;
    private static final String SQL_FIND_TRACKS = ;
    private static final String SQL_ADD_TRACK = ;
    private static final String SQL_REMOVE_TRACK = ;

    @Inject
    private Track.Builder trackEntityBuilder;

    @Inject
    private Playlist.Builder playlistEntityBuilder;

    @Override
    public List<Playlist> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, playlistEntityBuilder, offset, limit);
    }

    @Override
    public Optional<Playlist> findById(Integer id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, playlistEntityBuilder, id);
    }

    @Override
    public void save(Playlist playlist) throws HttpException {
        Integer playlistId = playlist.getId();
        if (playlistId == null) {
            QueryHelper.update(SQL_INSERT_ONE);
        }
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Track> getTracks(Integer playlistId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackEntityBuilder, playlistId, offset, limit);
    }

    @Override
    public void addTrack(Integer playlistId, Integer trackId) throws HttpException {
        QueryHelper.update(SQL_ADD_TRACK, playlistId, trackId);
    }

    @Override
    public void removeTrack(Integer playlistId, Integer trackId) throws HttpException {
        QueryHelper.update(SQL_REMOVE_TRACK, playlistId, trackId);
    }
}
