package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

public interface PlaylistRepository extends Repository<Playlist> {
    List<Playlist> findByName(String regex, int offset, int limit) throws RepositoryException;

    List<Track> getTracks(Long playListId, int offset, int limit) throws RepositoryException;

    void addTrack(Long playlistId, Long trackId) throws RepositoryException;

    void removeTrack(Long playlistId, Long trackId) throws RepositoryException;
}
