package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.repository.impl.TrackRepositoryImpl;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrackServiceImpl extends AbstractEntityService<Track, Long> implements TrackService {

    public static final TrackServiceImpl instance = new TrackServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final TrackRepository trackRepository = TrackRepositoryImpl.getInstance();

    private TrackServiceImpl() {
        this(DEFAULT_PAGE_SIZE);
    }

    private TrackServiceImpl(int pageSize) {
        super(pageSize);
    }

    public static TrackServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Track> findPage(int page) throws ServiceException {
        try {
            return trackRepository.findAll(getOffset(page), getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Track> findById(long id) {
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
    public void deleteById(long id) throws ServiceException {
        try {
            trackRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Track> findByName(String name, int page) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return trackRepository.findByName(buildRegex(name),
                    getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
