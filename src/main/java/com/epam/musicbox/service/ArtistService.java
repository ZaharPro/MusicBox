package com.epam.musicbox.service;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;

public interface ArtistService extends Service<Artist> {
    List<Artist> findByName(String name, int page) throws HttpException;

    List<Track> getTracks(Long artistId, int page) throws HttpException;

    void addTrack(Long artistId, Long trackId) throws HttpException;

    void removeTrack(Long artistId, Long trackId) throws HttpException;
}
