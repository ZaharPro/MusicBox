package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

/**
 * The interface Album repository.
 */
public interface AlbumRepository extends Repository<Album> {

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
    List<Album> findByName(String name, int offset, int limit) throws RepositoryException;

    /**
     * Count tracks long.
     *
     * @param albumId the album id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countTracks(long albumId) throws RepositoryException;

    /**
     * Gets tracks.
     *
     * @param albumId the album id
     * @param offset  the offset
     * @param limit   the limit
     * @return the tracks
     * @throws RepositoryException the repository exception
     */
    List<Track> getTracks(long albumId, int offset, int limit) throws RepositoryException;
}
