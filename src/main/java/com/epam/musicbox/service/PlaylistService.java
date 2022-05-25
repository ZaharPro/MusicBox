package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;

public interface PlaylistService extends Service<Playlist> {
    List<Track> getTracks(Integer playlistId, int page) throws HttpException;

    void addTrack(Integer playlistId, Integer trackId) throws HttpException;

    void removeTrack(Integer playlistId, Integer trackId) throws HttpException;
}
