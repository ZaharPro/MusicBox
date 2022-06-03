package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.repository.impl.AlbumRepositoryImpl;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.util.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AlbumServiceImpl implements AlbumService {
    public static final AlbumServiceImpl instance = new AlbumServiceImpl();

    private final AlbumRepository albumRepository = AlbumRepositoryImpl.getInstance();

    public static AlbumServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Album> findPage(int page) throws ServiceException {
        try {
            return albumRepository.findAll(Services.getOffset(page),
                    Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Album> findById(Long id) {
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
    public void deleteById(Long id) throws ServiceException {
        try {
            albumRepository.deleteById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Album> findByName(String name, int page) throws ServiceException {
        try {
            return name == null || name.length() < 2 ?
                    Collections.emptyList() :
                    albumRepository.findByName(Services.buildRegex(name),
                            Services.getOffset(page),
                            Services.PAGE_SIZE);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }
}
