package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface PlaylistService extends Service<Playlist> {
    Optional<Playlist> findByName(String name);

    Optional<Playlist> findByUser(Long userId);

    List<Track> getTracks(Long playlistId, int page) throws HttpException;

    void addTrack(Long playlistId, Long trackId) throws HttpException;

    void removeTrack(Long playlistId, Long trackId) throws HttpException;
}
