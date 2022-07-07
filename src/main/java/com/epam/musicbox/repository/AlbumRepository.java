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
     * @param regex the regex
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countByName(String regex) throws RepositoryException;

    /**
     * Find by name list.
     *
     * @param regex  the regex
     * @param offset the offset
     * @param limit  the limit
     * @return the list
     * @throws RepositoryException the repository exception
     */
    List<Album> findByName(String regex, int offset, int limit) throws RepositoryException;
}
