package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.repository.impl.TrackRepositoryImpl;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.List;

public class TrackServiceImpl extends AbstractEntityService<Track> implements TrackService {

    private static final TrackServiceImpl instance = new TrackServiceImpl();

    private final TrackRepository trackRepository = TrackRepositoryImpl.getInstance();

    private TrackServiceImpl() {
    }

    public static TrackServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected TrackRepository getRepository() {
        return trackRepository;
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
    public PageSearchResult<Track> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countByName(name);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Track> list = getRepository().findByName(name, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long countArtists(long trackId) throws ServiceException {
        try {
            return getRepository().countArtists(trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Artist> getArtists(long trackId, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countArtists(trackId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Artist> list = getRepository().getArtists(trackId, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
