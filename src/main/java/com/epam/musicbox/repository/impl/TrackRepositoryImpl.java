package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class TrackRepositoryImpl implements TrackRepository {
    private static final String SQL_FIND_ALL = ;
    private static final String SQL_FIND_BY_ID = ;
    private static final String SQL_INSERT_ONE = ;
    private static final String SQL_UPDATE_ONE = ;
    private static final String SQL_DELETE_BY_ID = ;
    private static final String SQL_FIND_BY_NAME = ;

    @Inject
    private Track.Builder trackBuilder;

    @Override
    public List<Track> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, trackBuilder, offset, limit);
    }

    @Override
    public Optional<Track> findById(Integer id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, trackBuilder, id);
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

    @Override
    public Optional<Track> findByName(String name) {
        return QueryHelper.queryOne(SQL_FIND_BY_NAME, trackBuilder, name);
    }
}
