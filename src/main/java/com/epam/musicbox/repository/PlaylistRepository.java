package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends Repository<Playlist> {
    Optional<Playlist> findByUser(Integer userId);

    Optional<Playlist> findByName(String name);

    List<Track> getTracks(Integer playListId, int offset, int limit) throws HttpException;

    void addTrack(Integer playlistId, Integer trackId) throws HttpException;

    void removeTrack(Integer playlistId, Integer trackId) throws HttpException;
}
