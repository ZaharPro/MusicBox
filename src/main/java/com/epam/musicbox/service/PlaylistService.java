package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface PlaylistService extends EntityService<Playlist> {

    long countByName(String regex) throws ServiceException;

    List<Playlist> findByName(String name, int page, int pageSize) throws ServiceException;

    long countTracks(long artistId) throws ServiceException;

    List<Track> getTracks(long playlistId, int page, int pageSize) throws ServiceException;

    void addTrack(long playlistId, long trackId) throws ServiceException;

    void removeTrack(long playlistId, long trackId) throws ServiceException;
}
