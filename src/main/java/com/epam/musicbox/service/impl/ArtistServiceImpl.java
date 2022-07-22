package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.impl.ArtistRepositoryImpl;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.List;

public class ArtistServiceImpl extends AbstractEntityService<Artist> implements ArtistService {

    private static final ArtistServiceImpl instance = new ArtistServiceImpl();

    private final ArtistRepository repository;

    private ArtistServiceImpl() {
        repository = ArtistRepositoryImpl.getInstance();
    }

    public static ArtistServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected ArtistRepository getRepository() {
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
    public PageSearchResult<Artist> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countByName(name);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Artist> list = getRepository().findByName(name, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long countTracks(long artistId) throws ServiceException {
        try {
            return getRepository().countTracks(artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Track> getTracks(long artistId, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countTracks(artistId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Track> list = getRepository().getTracks(artistId, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean hasTrack(long artistId, long trackId) throws ServiceException {
        try {
            return getRepository().hasTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void addTrack(long artistId, long trackId) throws ServiceException {
        try {
            getRepository().addTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void removeTrack(long artistId, long trackId) throws ServiceException {
        try {
            getRepository().removeTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long countAlbums(long artistId) throws ServiceException {
        try {
            return getRepository().countAlbums(artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageSearchResult<Album> getAlbums(long artistId, int page, int pageSize) throws ServiceException {
        try {
            if (!isValid(page, pageSize)) {
                return new PageSearchResult<>(page, pageSize);
            }
            long count = getRepository().countAlbums(artistId);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            List<Album> list = getRepository().getAlbums(artistId, getOffset(page, pageSize), pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
