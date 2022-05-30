package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends Repository<Playlist> {
    Optional<Playlist> findByName(String name);

    Optional<Playlist> findByUser(Long userId);

    List<Track> getTracks(Long playListId, int offset, int limit) throws HttpException;

    void addTrack(Long playlistId, Long trackId) throws HttpException;

    void removeTrack(Long playlistId, Long trackId) throws HttpException;
}
