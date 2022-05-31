package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.AlbumRepository;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.util.Services;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class AlbumServiceImpl implements AlbumService {
    @Inject
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findPage(int page) throws HttpException {
        return albumRepository.findAll(Services.getOffset(page),
                Services.PAGE_SIZE);
    }

    @Override
    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    @Override
    public void save(Album album) throws HttpException {
        albumRepository.save(album);
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        albumRepository.deleteById(id);
    }

    @Override
    public List<Album> findByName(String name, int page) throws HttpException {
        return albumRepository.findByName(Services.buildRegex(name),
                Services.getOffset(page),
                Services.PAGE_SIZE);
    }
}
