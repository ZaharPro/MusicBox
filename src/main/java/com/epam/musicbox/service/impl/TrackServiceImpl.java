package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.service.TrackService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class TrackServiceImpl implements TrackService {
    @Inject
    private TrackRepository trackRepository;

    @Override
    public List<Track> findPage(int page) throws HttpException {
        return trackRepository.findAll(Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public Optional<Track> findById(Long id) {
        return trackRepository.findById(id);
    }

    @Override
    public void save(Track track) throws HttpException {
        trackRepository.save(track);
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        trackRepository.deleteById(id);
    }

    @Override
    public Optional<Track> findByName(String name) {
        return trackRepository.findByName(name);
    }
}
