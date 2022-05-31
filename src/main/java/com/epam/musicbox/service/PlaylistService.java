package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;

public interface PlaylistService extends Service<Playlist> {
    List<Playlist> findByName(String name, int page) throws HttpException;

    List<Track> getTracks(Long playlistId, int page) throws HttpException;

    void addTrack(Long playlistId, Long trackId) throws HttpException;

    void removeTrack(Long playlistId, Long trackId) throws HttpException;
}
