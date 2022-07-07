package com.epam.musicbox.service;

import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

/**
 * The interface Track service.
 */
public interface TrackService extends EntityService<Track> {

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
    PageSearchResult<Track> findByName(String name, int page, int pageSize) throws ServiceException;


    /**
     * Count artists long.
     *
     * @param trackId the track id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countArtists(long trackId) throws ServiceException;

    /**
     * Gets artists.
     *
     * @param trackId  the track id
     * @param page     the page
     * @param pageSize the page size
     * @return the artists
     * @throws ServiceException the service exception
     */
    PageSearchResult<Artist> getArtists(long trackId, int page, int pageSize) throws ServiceException;
}
