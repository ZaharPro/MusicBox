package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.repository.impl.AlbumRepositoryImpl;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.List;

public class AlbumServiceImpl extends AbstractEntityService<Album> implements AlbumService {

    private static final AlbumServiceImpl instance = new AlbumServiceImpl();

    private final AlbumRepository repository;

    private AlbumServiceImpl() {
        repository = AlbumRepositoryImpl.getInstance();
    }

    public static AlbumServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected AlbumRepository getRepository() {
        return repository;
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return getRepository().countByName(name);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Album> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countByName(name);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Album> list = getRepository().findByName(name, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long countTracks(long userId) throws ServiceException {
        try {
            return getRepository().countTracks(userId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Track> getTracks(long userId, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countTracks(userId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Track> list = getRepository().getTracks(userId, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
