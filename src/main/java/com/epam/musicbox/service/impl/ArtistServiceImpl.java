package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.impl.ArtistRepositoryImpl;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.util.ServiceUtils;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArtistServiceImpl implements ArtistService {

    private static final ArtistServiceImpl instance = new ArtistServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final ArtistRepository artistRepository = ArtistRepositoryImpl.getInstance();

    private ArtistServiceImpl() {
    }

    public static ArtistServiceImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws ServiceException {
        try {
            return artistRepository.count();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Artist> findPage(int page, int pageSize) throws ServiceException {
        try {
            return artistRepository.findAll(ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Artist> findById(long id) throws ServiceException {
        try {
            return artistRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long save(Artist artist) throws ServiceException {
        try {
            return artistRepository.save(artist);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            artistRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return artistRepository.countByName(ServiceUtils.buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Artist> findByName(String name, int page, int pageSize) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return artistRepository.findByName(ServiceUtils.buildRegex(name),
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countTracks(long artistId) throws ServiceException {
        try {
            return artistRepository.countTracks(artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getTracks(long artistId, int page, int pageSize) throws ServiceException {
        try {
            return artistRepository.getTracks(artistId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTrack(long artistId, long trackId) throws ServiceException {
        try {
            artistRepository.addTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTrack(long artistId, long trackId) throws ServiceException {
        try {
            artistRepository.removeTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> getAlbums(long artistId, int page, int pageSize) throws ServiceException {
        try {
            return artistRepository.getAlbums(artistId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
