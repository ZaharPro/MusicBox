package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.TrackRepository;
import com.epam.musicbox.repository.impl.TrackRepositoryImpl;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.List;

public class TrackServiceImpl extends AbstractEntityService<Track> implements TrackService {

    private static final TrackServiceImpl instance = new TrackServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final TrackRepository trackRepository = TrackRepositoryImpl.getInstance();

    private TrackServiceImpl() {
    }

    public static TrackServiceImpl getInstance() {
        return instance;
    }

    @Override
    protected TrackRepository getRepository() {
        return trackRepository;
    }

    @Override
    public long countByName(String name) throws ServiceException {
        try {
            return getRepository().countByName(buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PageSearchResult<Track> findByName(String name, int page, int pageSize) throws ServiceException {
        try {
            if (!validator.isValidName(name)) {
                return new PageSearchResult<>(page, pageSize);
            }

            String regex = buildRegex(name);
            TrackRepository repository = getRepository();
            long count = repository.countByName(name);
            if (count == 0) {
                return new PageSearchResult<>(page, pageSize);
            }
            int offset = getOffset(page, pageSize);
            List<Track> list = repository.findByName(regex, offset, pageSize);
            return new PageSearchResult<>(page, pageSize, count, list);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
