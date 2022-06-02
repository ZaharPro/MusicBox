package com.epam.musicbox.service.impl;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.repository.ArtistRepository;
import com.epam.musicbox.repository.impl.ArtistRepositoryImpl;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.util.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArtistServiceImpl implements ArtistService {

    public static final ArtistServiceImpl instance = new ArtistServiceImpl();

    public static ArtistServiceImpl getInstance() {
        return instance;
    }

    private final ArtistRepository artistRepository = ArtistRepositoryImpl.getInstance();

    @Override
    public List<Artist> findPage(int page) throws HttpException {
        return artistRepository.findAll(Services.getOffset(page),
                Services.PAGE_SIZE);
    }

    @Override
    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public void save(Artist artist) throws HttpException {
        artistRepository.save(artist);
    }

    @Override
    public void deleteById(Long id) throws HttpException {
        artistRepository.deleteById(id);
    }

    @Override
    public List<Artist> findByName(String name, int page) throws HttpException {
        return name.length() < 2 ?
                Collections.emptyList() :
                artistRepository.findByName(Services.buildRegex(name),
                        Services.getOffset(page),
                        Services.PAGE_SIZE);
    }

    @Override
    public List<Track> getTracks(Long artistId, int page) throws HttpException {
        return artistRepository.getTracks(artistId,
                Services.getOffset(page),
                Services.PAGE_SIZE);
    }

    @Override
    public void addTrack(Long artistId, Long trackId) throws HttpException {
        artistRepository.addTrack(artistId, trackId);
    }

    @Override
    public void removeTrack(Long artistId, Long trackId) throws HttpException {
        artistRepository.removeTrack(artistId, trackId);
    }
}
