package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;

public interface PlaylistRepository extends Repository<Playlist> {
    List<Track> getTracks(Integer playListId, int offset, int limit) throws HttpException;

    void addTrack(Integer playlistId, Integer trackId) throws HttpException;

    void removeTrack(Integer playlistId, Integer trackId) throws HttpException;
}
