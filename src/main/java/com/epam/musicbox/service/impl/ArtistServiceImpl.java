package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.impl.ArtistRepositoryImpl;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArtistServiceImpl extends AbstractEntityService<Artist, Long> implements ArtistService {

    public static final ArtistServiceImpl instance = new ArtistServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final ArtistRepository artistRepository = ArtistRepositoryImpl.getInstance();

    private ArtistServiceImpl() {
        this(DEFAULT_PAGE_SIZE);
    }

    private ArtistServiceImpl(int pageSize) {
        super(pageSize);
    }

    public static ArtistServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Artist> findPage(int page) throws ServiceException {
        try {
            return artistRepository.findAll(getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Artist> findById(long id) {
        return artistRepository.findById(id);
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
    public List<Artist> findByName(String name, int page) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return artistRepository.findByName(buildRegex(name),
                    getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getTracks(long artistId, int page) throws ServiceException {
        try {
            return artistRepository.getTracks(artistId,
                    getOffset(page),
                    getPageSize());
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
    public List<Album> getAlbums(long artistId, int page) throws ServiceException {
        try {
            return artistRepository.getAlbums(artistId,
                    getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
