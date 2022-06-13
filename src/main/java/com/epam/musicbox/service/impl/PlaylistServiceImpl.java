package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.impl.PlaylistRepositoryImpl;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlaylistServiceImpl extends AbstractEntityService<Playlist, Long> implements PlaylistService {

    public static final PlaylistServiceImpl instance = new PlaylistServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final PlaylistRepository playlistRepository = PlaylistRepositoryImpl.getInstance();

    private PlaylistServiceImpl() {
        this(DEFAULT_PAGE_SIZE);
    }

    private PlaylistServiceImpl(int pageSize) {
        super(pageSize);
    }

    public static PlaylistServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Playlist> findPage(int page) throws ServiceException {
        try {
            return playlistRepository.findAll(getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Playlist> findById(long id) {
        return playlistRepository.findById(id);
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
    public List<Playlist> findByName(String name, int page) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return playlistRepository.findByName(buildRegex(name),
                    getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getTracks(long playlistId, int page) throws ServiceException {
        try {
            return playlistRepository.getTracks(playlistId,
                    getOffset(page),
                    getPageSize());
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
