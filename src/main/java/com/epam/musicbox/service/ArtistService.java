package com.epam.musicbox.service;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface ArtistService extends Service<Artist> {
    Optional<Artist> findByName(String name);

    List<Track> getTracks(Integer artistId, int page) throws HttpException;

    void addTrack(Integer artistId, Integer trackId) throws HttpException;

    void removeTrack(Integer artistId, Integer trackId) throws HttpException;
}
