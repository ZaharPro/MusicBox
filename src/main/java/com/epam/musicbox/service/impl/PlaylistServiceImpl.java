package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.impl.PlaylistRepositoryImpl;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.ServiceUtils;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlaylistServiceImpl implements PlaylistService {

    private static final PlaylistServiceImpl instance = new PlaylistServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final PlaylistRepository playlistRepository = PlaylistRepositoryImpl.getInstance();

    private PlaylistServiceImpl() {
    }

    public static PlaylistServiceImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws ServiceException {
        try {
            return playlistRepository.count();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> findPage(int page, int pageSize) throws ServiceException {
        try {
            return playlistRepository.findAll(ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Playlist> findById(long id) throws ServiceException {
        try {
            return playlistRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long save(Playlist playlist) throws ServiceException {
        try {
            return playlistRepository.save(playlist);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            playlistRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return playlistRepository.countByName(ServiceUtils.buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> findByName(String name, int page, int pageSize) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return playlistRepository.findByName(ServiceUtils.buildRegex(name),
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countTracks(long artistId) throws ServiceException {
        try {
            return playlistRepository.countTracks(artistId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getTracks(long playlistId, int page, int pageSize) throws ServiceException {
        try {
            return playlistRepository.getTracks(playlistId,
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTrack(long playlistId, long trackId) throws ServiceException {
        try {
            playlistRepository.addTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTrack(long playlistId, long trackId) throws ServiceException {
        try {
            playlistRepository.removeTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
