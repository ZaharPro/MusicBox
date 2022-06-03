package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface PlaylistService extends Service<Playlist> {
    List<Playlist> findByName(String name, int page) throws ServiceException;

    List<Track> getTracks(Long playlistId, int page) throws ServiceException;

    void addTrack(Long playlistId, Long trackId) throws ServiceException;

    void removeTrack(Long playlistId, Long trackId) throws ServiceException;
}
