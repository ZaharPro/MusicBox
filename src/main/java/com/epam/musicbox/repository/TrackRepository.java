package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

/**
 * The interface Track repository.
 */
public interface TrackRepository extends Repository<Track> {

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
    List<Track> findByName(String name, int offset, int limit) throws RepositoryException;


    /**
     * Count artists long.
     *
     * @param trackId the track id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countArtists(long trackId) throws RepositoryException;

    /**
     * Gets artists.
     *
     * @param trackId the track id
     * @param offset  the offset
     * @param limit   the limit
     * @return the artists
     * @throws RepositoryException the repository exception
     */
    List<Artist> getArtists(long trackId, int offset, int limit) throws RepositoryException;
}
