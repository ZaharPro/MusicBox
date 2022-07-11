package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

/**
 * The interface Playlist repository.
 */
public interface PlaylistRepository extends Repository<Playlist> {

    /**
     * Count by name long.
     *
     * @param name the name
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countByName(String name) throws RepositoryException;

    /**
     * Find by name list.
     *
     * @param name   the name
     * @param offset the offset
     * @param limit  the limit
     * @return the list
     * @throws RepositoryException the repository exception
     */
    List<Playlist> findByName(String name, int offset, int limit) throws RepositoryException;


    /**
     * Count tracks long.
     *
     * @param playlistId the playlist id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countTracks(long playlistId) throws RepositoryException;

    /**
     * Gets tracks.
     *
     * @param playlistId the playlist id
     * @param offset     the offset
     * @param limit      the limit
     * @return the tracks
     * @throws RepositoryException the repository exception
     */
    List<Track> getTracks(long playlistId, int offset, int limit) throws RepositoryException;

    /**
     * Has track boolean.
     *
     * @param playlistId the playlist id
     * @param trackId    the track id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean hasTrack(long playlistId, long trackId) throws RepositoryException;

    /**
     * Add track.
     *
     * @param playlistId the playlist id
     * @param trackId    the track id
     * @throws RepositoryException the repository exception
     */
    void addTrack(long playlistId, long trackId) throws RepositoryException;

    /**
     * Remove track.
     *
     * @param playlistId the playlist id
     * @param trackId    the track id
     * @throws RepositoryException the repository exception
     */
    void removeTrack(long playlistId, long trackId) throws RepositoryException;
}
