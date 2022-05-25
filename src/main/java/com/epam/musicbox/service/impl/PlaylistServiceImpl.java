package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.PlaylistRepository;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.Service;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class PlaylistServiceImpl implements PlaylistService {
    @Inject
    private PlaylistRepository playlistRepository;

    @Override
    public List<Playlist> findPage(int page) throws HttpException {
        return playlistRepository.findAll(Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public Optional<Playlist> findById(Integer id) {
        return playlistRepository.findById(id);
    }

    @Override
    public void save(Playlist playlist) throws HttpException {
        playlistRepository.save(playlist);
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        playlistRepository.deleteById(id);
    }

    @Override
    public List<Track> getTracks(Integer playlistId, int page) throws HttpException {
        return playlistRepository.getTracks(playlistId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }
}
