package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

public interface PlaylistRepository extends Repository<Playlist> {

    long countByName(String regex) throws RepositoryException;

    List<Playlist> findByName(String regex, int offset, int limit) throws RepositoryException;


    long countTracks(long playlistId) throws RepositoryException;

    List<Track> getTracks(long playlistId, int offset, int limit) throws RepositoryException;

    boolean hasTrack(long playlistId, long trackId) throws RepositoryException;

    void addTrack(long playlistId, long trackId) throws RepositoryException;

    void removeTrack(long playlistId, long trackId) throws RepositoryException;
}
