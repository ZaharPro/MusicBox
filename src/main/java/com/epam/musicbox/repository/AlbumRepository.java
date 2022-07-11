package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
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
}
