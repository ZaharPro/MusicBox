package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.service.TrackService;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;

    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Track> findPage(int page) throws HttpException {
        return trackRepository.findAll(Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public Optional<Track> findById(Integer id) throws HttpException {
        return trackRepository.findById(id);
    }

    @Override
    public void save(Track track) throws HttpException {
        trackRepository.save(track);
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        trackRepository.deleteById(id);
    }
}
