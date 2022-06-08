package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface PlaylistService extends Service<Playlist> {
    List<Playlist> findByName(String name, int page) throws ServiceException;

    List<Track> getTracks(long playlistId, int page) throws ServiceException;

    void addTrack(long playlistId, long trackId) throws ServiceException;

    void removeTrack(long playlistId, long trackId) throws ServiceException;
}
