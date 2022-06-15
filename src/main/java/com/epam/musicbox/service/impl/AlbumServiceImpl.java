package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.repository.impl.AlbumRepositoryImpl;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.util.ServiceUtils;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AlbumServiceImpl implements AlbumService {

    private static final AlbumServiceImpl instance = new AlbumServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private final AlbumRepository albumRepository = AlbumRepositoryImpl.getInstance();

    private AlbumServiceImpl() {
    }

    public static AlbumServiceImpl getInstance() {
        return instance;
    }

    @Override
    public long count() throws ServiceException {
        try {
            return albumRepository.count();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> findPage(int page, int pageSize) throws ServiceException {
        try {
            return albumRepository.findAll(ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Album> findById(long id) throws ServiceException {
        try {
            return albumRepository.findById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
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
    public long countByName(String name) throws ServiceException {
        try {
            return albumRepository.countByName(ServiceUtils.buildRegex(name));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> findByName(String name, int page, int pageSize) throws ServiceException {
        if (!validator.isValidName(name)) {
            return Collections.emptyList();
        }
        try {
            return albumRepository.findByName(ServiceUtils.buildRegex(name),
                    ServiceUtils.getOffset(page, pageSize),
                    pageSize);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
