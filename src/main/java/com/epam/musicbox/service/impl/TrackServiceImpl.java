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
            return getRepository().countByName(buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Track> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!isValidPage(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }

            TrackRepository repository = getRepository();
            String regex = buildRegex(name);
            long count = repository.countByName(regex);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Track> list = repository.findByName(regex, offset, pageSize);
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
            if (!isValidPage(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            TrackRepository repository = getRepository();
            long count = repository.countArtists(trackId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Artist> list = repository.getArtists(trackId, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
