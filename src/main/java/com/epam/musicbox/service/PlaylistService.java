package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.psr.PageSearchResult;

public interface PlaylistService extends EntityService<Playlist> {

    long countByName(String name) throws ServiceException;

    PageSearchResult<Playlist> findByName(String name, int page, int pageSize) throws ServiceException;


    long countTracks(long artistId) throws ServiceException;

    PageSearchResult<Track> getTracks(long playlistId, int page, int pageSize) throws ServiceException;

    boolean hasTrack(long playlistId, long trackId) throws ServiceException;

    void addTrack(long playlistId, long trackId) throws ServiceException;

    void removeTrack(long playlistId, long trackId) throws ServiceException;
}
