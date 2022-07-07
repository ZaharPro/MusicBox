package com.epam.musicbox.service;

import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

/**
 * The interface Playlist service.
 */
public interface PlaylistService extends EntityService<Playlist> {

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
    PageSearchResult<Playlist> findByName(String name, int page, int pageSize) throws ServiceException;


    /**
     * Count tracks long.
     *
     * @param artistId the artist id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countTracks(long artistId) throws ServiceException;

    /**
     * Gets tracks.
     *
     * @param playlistId the playlist id
     * @param page       the page
     * @param pageSize   the page size
     * @return the tracks
     * @throws ServiceException the service exception
     */
    PageSearchResult<Track> getTracks(long playlistId, int page, int pageSize) throws ServiceException;

    /**
     * Has track boolean.
     *
     * @param playlistId the playlist id
     * @param trackId    the track id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean hasTrack(long playlistId, long trackId) throws ServiceException;

    /**
     * Add track.
     *
     * @param playlistId the playlist id
     * @param trackId    the track id
     * @throws ServiceException the service exception
     */
    void addTrack(long playlistId, long trackId) throws ServiceException;

    /**
     * Remove track.
     *
     * @param playlistId the playlist id
     * @param trackId    the track id
     * @throws ServiceException the service exception
     */
    void removeTrack(long playlistId, long trackId) throws ServiceException;
}
