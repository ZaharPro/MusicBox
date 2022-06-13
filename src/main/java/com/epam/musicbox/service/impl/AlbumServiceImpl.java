package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.repository.impl.AlbumRepositoryImpl;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AlbumServiceImpl extends AbstractEntityService<Album, Long> implements AlbumService {

    public static final AlbumServiceImpl instance = new AlbumServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final AlbumRepository albumRepository = AlbumRepositoryImpl.getInstance();

    private AlbumServiceImpl() {
        this(DEFAULT_PAGE_SIZE);
    }

    private AlbumServiceImpl(int pageSize) {
        super(pageSize);
    }

    public static AlbumServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Album> findPage(int page) throws ServiceException {
        try {
            return albumRepository.findAll(getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Album> findById(long id) {
        return albumRepository.findById(id);
    }

    @Override
    public long save(Album album) throws ServiceException {
        try {
            return albumRepository.save(album);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            albumRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> findByName(String name, int page) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return albumRepository.findByName(buildRegex(name),
                    getOffset(page),
                    getPageSize());
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
