package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.repository.impl.PlaylistRepositoryImpl;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.Services;

import java.util.List;
import java.util.Optional;

public class PlaylistServiceImpl implements PlaylistService {

    public static final PlaylistServiceImpl instance = new PlaylistServiceImpl();

    private final PlaylistRepository playlistRepository = PlaylistRepositoryImpl.getInstance();

    public static PlaylistServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Playlist> findPage(int page) throws HttpException {
        return playlistRepository.findAll(Services.getOffset(page),
                Services.PAGE_SIZE);
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public void save(Playlist playlist) throws HttpException {
        playlistRepository.save(playlist);
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        playlistRepository.deleteById(id);
    }

    @Override
    public List<Playlist> findByName(String name, int page) throws HttpException {
        return playlistRepository.findByName(Services.buildRegex(name),
                Services.getOffset(page),
                Services.PAGE_SIZE);
    }

    @Override
    public List<Track> getTracks(Long playlistId, int page) throws HttpException {
        return playlistRepository.getTracks(playlistId,
                Services.getOffset(page),
                Services.PAGE_SIZE);
    }

    @Override
    public void addTrack(Long playlistId, Long trackId) throws HttpException {
        playlistRepository.addTrack(playlistId, trackId);
    }

    @Override
    public void removeTrack(Long playlistId, Long trackId) throws HttpException {
        playlistRepository.removeTrack(playlistId, trackId);
    }
}
