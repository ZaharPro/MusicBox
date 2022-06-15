package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.repository.impl.TrackRepositoryImpl;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.util.ServiceUtils;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrackServiceImpl implements TrackService {

    private static final TrackServiceImpl instance = new TrackServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final TrackRepository trackRepository = TrackRepositoryImpl.getInstance();

    private TrackServiceImpl() {
    }

    public static TrackServiceImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws ServiceException {
        try {
            return trackRepository.count();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> findPage(int page, int pageSize) throws ServiceException {
        try {
            return trackRepository.findAll(ServiceUtils.getOffset(page, pageSize), pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Track> findById(long id) throws ServiceException {
        try {
            return trackRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
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
    public void deleteById(long id) throws ServiceException {
        try {
            trackRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return trackRepository.countByName(ServiceUtils.buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> findByName(String name, int page, int pageSize) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return trackRepository.findByName(ServiceUtils.buildRegex(name),
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
