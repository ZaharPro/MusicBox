package com.epam.musicbox.service;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface ArtistService extends Service<Artist> {
    List<Artist> findByName(String name, int page) throws ServiceException;

    List<Track> getTracks(Long artistId, int page) throws ServiceException;

    void addTrack(Long artistId, Long trackId) throws ServiceException;

    void removeTrack(Long artistId, Long trackId) throws ServiceException;
}
