package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.psr.PageSearchResult;

public interface ArtistService extends EntityService<Artist> {

    long countByName(String regex) throws ServiceException;

    PageSearchResult<Artist> findByName(String name, int page, int pageSize) throws ServiceException;


    long countTracks(long artistId) throws ServiceException;

    PageSearchResult<Track> getTracks(long artistId, int page, int pageSize) throws ServiceException;

    void addTrack(long artistId, long trackId) throws ServiceException;

    void removeTrack(long artistId, long trackId) throws ServiceException;

    long countAlbums(long artistId) throws ServiceException;

    PageSearchResult<Album> getAlbums(long artistId, int page, int pageSize) throws ServiceException;
}
