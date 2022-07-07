package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

/**
 * The interface Artist service.
 */
public interface ArtistService extends EntityService<Artist> {

    /**
     * Count by name long.
     *
     * @param regex the regex
     * @return the long
     * @throws ServiceException the service exception
     */
    long countByName(String regex) throws ServiceException;

    /**
     * Find by name page search result.
     *
     * @param name     the name
     * @param page     the page
     * @param pageSize the page size
     * @return the page search result
     * @throws ServiceException the service exception
     */
    PageSearchResult<Artist> findByName(String name, int page, int pageSize) throws ServiceException;


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
     * @param artistId the artist id
     * @param page     the page
     * @param pageSize the page size
     * @return the tracks
     * @throws ServiceException the service exception
     */
    PageSearchResult<Track> getTracks(long artistId, int page, int pageSize) throws ServiceException;

    /**
     * Has track boolean.
     *
     * @param artistId the artist id
     * @param trackId  the track id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean hasTrack(long artistId, long trackId) throws ServiceException;

    /**
     * Add track.
     *
     * @param artistId the artist id
     * @param trackId  the track id
     * @throws ServiceException the service exception
     */
    void addTrack(long artistId, long trackId) throws ServiceException;

    /**
     * Remove track.
     *
     * @param artistId the artist id
     * @param trackId  the track id
     * @throws ServiceException the service exception
     */
    void removeTrack(long artistId, long trackId) throws ServiceException;


    /**
     * Count albums long.
     *
     * @param artistId the artist id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countAlbums(long artistId) throws ServiceException;

    /**
     * Gets albums.
     *
     * @param artistId the artist id
     * @param page     the page
     * @param pageSize the page size
     * @return the albums
     * @throws ServiceException the service exception
     */
    PageSearchResult<Album> getAlbums(long artistId, int page, int pageSize) throws ServiceException;
}
