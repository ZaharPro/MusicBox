package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.impl.ArtistRepositoryImpl;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.util.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArtistServiceImpl implements ArtistService {

    public static final ArtistServiceImpl instance = new ArtistServiceImpl();

    public static ArtistServiceImpl getInstance() {
        return instance;
    }

    private final ArtistRepository artistRepository = ArtistRepositoryImpl.getInstance();

    @Override
    public List<Artist> findPage(int page) throws ServiceException {
        try {
            return artistRepository.findAll(Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Artist> findById(Long id) {
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
    public void deleteById(Long id) throws ServiceException {
        try {
            artistRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Artist> findByName(String name, int page) throws ServiceException {
        try {
            return name == null || name.length() < 2 ?
                    Collections.emptyList() :
                    artistRepository.findByName(Services.buildRegex(name),
                            Services.getOffset(page),
                            Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getTracks(Long artistId, int page) throws ServiceException {
        try {
            return artistRepository.getTracks(artistId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTrack(Long artistId, Long trackId) throws ServiceException {
        try {
            artistRepository.addTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTrack(Long artistId, Long trackId) throws ServiceException {
        try {
            artistRepository.removeTrack(artistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
