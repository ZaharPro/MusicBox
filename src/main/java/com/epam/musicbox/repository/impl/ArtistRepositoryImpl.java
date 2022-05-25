package com.epam.musicbox.repository.impl;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.*;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ArtistRepositoryImpl implements ArtistRepository {
    private static final String SQL_FIND_ALL = ;
    private static final String SQL_FIND_BY_ID = ;
    private static final String SQL_INSERT_ONE = ;
    private static final String SQL_UPDATE_ONE = ;
    private static final String SQL_DELETE_BY_ID = ;
    private static final String SQL_FIND_TRACKS = ;

    @Inject
    private Track.Builder trackEntityBuilder;

    @Inject
    private Artist.Builder artistEntityBuilder;

    @Override
    public List<Artist> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, artistEntityBuilder, offset, limit);
    }

    @Override
    public Optional<Artist> findById(Integer id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, artistEntityBuilder, id);
    }

    @Override
    public void save(Artist artist) throws HttpException {
        Integer artistId = artist.getId();
        if (artistId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    artist.getName(),
                    artist.getAvatar());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    artist.getId(),
                    artist.getName(),
                    artist.getAvatar());
        }
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public List<Track> getTracks(Integer artistId, int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_TRACKS, trackEntityBuilder, artistId, offset, limit);
    }
}
