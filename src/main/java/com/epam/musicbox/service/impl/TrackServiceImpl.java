package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
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
    public List<Track> findPage(int page) throws ServiceException {
        try {
            return trackRepository.findAll(Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Track> findById(Long id) {
        return trackRepository.findById(id);
    }

    @Override
    public long save(Track track) throws ServiceException {
        try {
            return trackRepository.save(track);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            trackRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> findByName(String name, int page) throws ServiceException {
        try {
            return name == null || name.length() < 2 ?
                    Collections.emptyList() :
                    trackRepository.findByName(Services.buildRegex(name),
                            Services.getOffset(page),
                            Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
