package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

public interface PlaylistRepository extends Repository<Playlist> {

    long countByName(String regex) throws RepositoryException;

    List<Playlist> findByName(String regex, int offset, int limit) throws RepositoryException;

    long countTracks(long artistId) throws RepositoryException;

    List<Track> getTracks(long artistId, int offset, int limit) throws RepositoryException;

    void addTrack(long artistId, long trackId) throws RepositoryException;

    void removeTrack(long artistId, long trackId) throws RepositoryException;
}
