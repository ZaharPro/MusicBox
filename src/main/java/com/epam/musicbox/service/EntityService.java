package com.epam.musicbox.service;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.Optional;

/**
 * The interface Entity service.
 *
 * @param <T> the type parameter
 */
public interface EntityService<T extends Entity> {

    /**
     * Count long.
     *
     * @return the long
     * @throws ServiceException the service exception
     */
    long count() throws ServiceException;

    /**
     * Find page page search result.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the page search result
     * @throws ServiceException the service exception
     */
    PageSearchResult<T> findPage(int page, int pageSize) throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<T> findById(long id) throws ServiceException;

    /**
     * Save long.
     *
     * @param t the t
     * @return the long
     * @throws ServiceException the service exception
     */
    long save(T t) throws ServiceException;

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws ServiceException the service exception
     */
    void deleteById(long id) throws ServiceException;
}