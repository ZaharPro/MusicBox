package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.repository.impl.TrackRepositoryImpl;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.util.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrackServiceImpl implements TrackService {

    public static final TrackServiceImpl instance = new TrackServiceImpl();

    private final TrackRepository trackRepository = TrackRepositoryImpl.getInstance();

    public static TrackServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Track> findPage(int page) throws HttpException {
        return trackRepository.findAll(Services.getOffset(page),
                Services.PAGE_SIZE);
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
    public List<Track> findByName(String name, int page) throws HttpException {
        return name == null || name.length() < 2 ?
                Collections.emptyList() :
                trackRepository.findByName(Services.buildRegex(name),
                        Services.getOffset(page),
                        Services.PAGE_SIZE);
    }
}
