package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.impl.PlaylistRepositoryImpl;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlaylistServiceImpl implements PlaylistService {

    public static final PlaylistServiceImpl instance = new PlaylistServiceImpl();

    private final PlaylistRepository playlistRepository = PlaylistRepositoryImpl.getInstance();

    public static PlaylistServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Playlist> findPage(int page) throws ServiceException {
        try {
            return playlistRepository.findAll(Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Playlist> findById(Long id) {
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
    public void deleteById(Long id) throws ServiceException {
        try {
            playlistRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Playlist> findByName(String name, int page) throws ServiceException {
        try {
            return name == null || name.length() < 2 ?
                    Collections.emptyList() :
                    playlistRepository.findByName(Services.buildRegex(name),
                            Services.getOffset(page),
                            Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> getTracks(Long playlistId, int page) throws ServiceException {
        try {
            return playlistRepository.getTracks(playlistId,
                    Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addTrack(Long playlistId, Long trackId) throws ServiceException {
        try {
            playlistRepository.addTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeTrack(Long playlistId, Long trackId) throws ServiceException {
        try {
            playlistRepository.removeTrack(playlistId, trackId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
