package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface ArtistService extends EntityService<Artist, Long> {
    List<Artist> findByName(String name, int page) throws ServiceException;

    List<Track> getTracks(long artistId, int page) throws ServiceException;

    void addTrack(long artistId, long trackId) throws ServiceException;

    void removeTrack(long artistId, long trackId) throws ServiceException;

    List<Album> getAlbums(long artistId, int page) throws ServiceException;
}
