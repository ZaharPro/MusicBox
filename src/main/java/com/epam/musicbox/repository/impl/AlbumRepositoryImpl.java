package com.epam.musicbox.repository.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.util.QueryHelper;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class AlbumRepositoryImpl implements AlbumRepository {
    private static final String SQL_FIND_ALL = ;
    private static final String SQL_FIND_BY_ID = ;
    private static final String SQL_INSERT_ONE = ;
    private static final String SQL_UPDATE_ONE = ;
    private static final String SQL_DELETE_BY_ID = ;
    private static final String SQL_FIND_BY_NAME = ;

    @Inject
    private Album.Builder albumEntityBuilder;

    @Override
    public List<Album> findAll(int offset, int limit) throws HttpException {
        return QueryHelper.queryAll(SQL_FIND_ALL, albumEntityBuilder, offset, limit);
    }

    @Override
    public Optional<Album> findById(Long id) {
        return QueryHelper.queryOne(SQL_FIND_BY_ID, albumEntityBuilder, id);
    }

    @Override
    public void save(Album artist) throws HttpException {
        Long artistId = artist.getId();
        if (artistId == null) {
            QueryHelper.update(SQL_INSERT_ONE,
                    artist.getName(),
                    artist.getPicture());
        } else {
            QueryHelper.update(SQL_UPDATE_ONE,
                    artist.getId(),
                    artist.getName(),
                    artist.getPicture());
        }
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        QueryHelper.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<Album> findByName(String name) {
        return QueryHelper.queryOne(SQL_FIND_BY_NAME, albumEntityBuilder, name);
    }
}
