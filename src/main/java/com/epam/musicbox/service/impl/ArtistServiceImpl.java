package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.Service;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> findPage(int page) throws HttpException {
        return artistRepository.findAll(Service.getOffset(page),
                Service.PAGE_SIZE);
    }

    @Override
    public Optional<Artist> findById(Integer id) throws HttpException {
        return artistRepository.findById(id);
    }

    @Override
    public void save(Artist artist) throws HttpException {
        artistRepository.save(artist);
    }

    @Override
    public void deleteById(Integer id) throws HttpException {
        artistRepository.deleteById(id);
    }

    @Override
    public List<Track> getTracks(Integer artistId, int page) throws HttpException {
        return artistRepository.getTracks(artistId,
                Service.getOffset(page),
                Service.PAGE_SIZE);
    }
}
