package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Repository.
 *
 * @param <T> the type parameter
 */
public interface Repository<T extends Entity> {

    /**
     * Count long.
     *
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long count() throws RepositoryException;

    /**
     * Find all list.
     *
     * @param offset the offset
     * @param limit  the limit
     * @return the list
     * @throws RepositoryException the repository exception
     */
    List<T> findAll(int offset, int limit) throws RepositoryException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws RepositoryException the repository exception
     */
    Optional<T> findById(long id) throws RepositoryException;

    /**
     * Save long.
     *
     * @param t the t
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long save(T t) throws RepositoryException;

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws RepositoryException the repository exception
     */
    void deleteById(long id) throws RepositoryException;
}