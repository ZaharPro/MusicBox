package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

/**
 * The interface Album service.
 */
public interface AlbumService extends EntityService<Album> {

    /**
     * Count by name long.
     *
     * @param name the name
     * @return the long
     * @throws ServiceException the service exception
     */
    long countByName(String name) throws ServiceException;

    /**
     * Find by name page search result.
     *
     * @param name     the name
     * @param page     the page
     * @param pageSize the page size
     * @return the page search result
     * @throws ServiceException the service exception
     */
    PageSearchResult<Album> findByName(String name, int page, int pageSize) throws ServiceException;
}
