package com.epam.musicbox.service;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService extends EntityService<User> {

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findByLogin(String login) throws ServiceException;

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findByEmail(String email) throws ServiceException;


    /**
     * Count by role long.
     *
     * @param roleId the role id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countByRole(int roleId) throws ServiceException;

    /**
     * Find by role page search result.
     *
     * @param roleId   the role id
     * @param page     the page
     * @param pageSize the page size
     * @return the page search result
     * @throws ServiceException the service exception
     */
    PageSearchResult<User> findByRole(int roleId, int page, int pageSize) throws ServiceException;

    /**
     * Sets role.
     *
     * @param userId the user id
     * @param roleId the role id
     * @throws ServiceException the service exception
     */
    void setRole(long userId, int roleId) throws ServiceException;


    /**
     * Count liked tracks long.
     *
     * @param userId the user id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countLikedTracks(long userId) throws ServiceException;

    /**
     * Gets liked tracks.
     *
     * @param userId   the user id
     * @param page     the page
     * @param pageSize the page size
     * @return the liked tracks
     * @throws ServiceException the service exception
     */
    PageSearchResult<Track> getLikedTracks(long userId, int page, int pageSize) throws ServiceException;

    /**
     * Is liked track boolean.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLikedTrack(long userId, long trackId) throws ServiceException;

    /**
     * Mark liked track.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @throws ServiceException the service exception
     */
    void markLikedTrack(long userId, long trackId) throws ServiceException;

    /**
     * Unmark liked track.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @throws ServiceException the service exception
     */
    void unmarkLikedTrack(long userId, long trackId) throws ServiceException;


    /**
     * Count liked albums long.
     *
     * @param userId the user id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countLikedAlbums(long userId) throws ServiceException;

    /**
     * Gets liked albums.
     *
     * @param userId   the user id
     * @param page     the page
     * @param pageSize the page size
     * @return the liked albums
     * @throws ServiceException the service exception
     */
    PageSearchResult<Album> getLikedAlbums(long userId, int page, int pageSize) throws ServiceException;

    /**
     * Is liked album boolean.
     *
     * @param userId  the user id
     * @param albumId the album id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLikedAlbum(long userId, long albumId) throws ServiceException;

    /**
     * Mark liked album.
     *
     * @param userId  the user id
     * @param albumId the album id
     * @throws ServiceException the service exception
     */
    void markLikedAlbum(long userId, long albumId) throws ServiceException;

    /**
     * Unmark liked album.
     *
     * @param userId  the user id
     * @param albumId the album id
     * @throws ServiceException the service exception
     */
    void unmarkLikedAlbum(long userId, long albumId) throws ServiceException;


    /**
     * Count liked artists long.
     *
     * @param userId the user id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countLikedArtists(long userId) throws ServiceException;

    /**
     * Gets liked artists.
     *
     * @param userId   the user id
     * @param page     the page
     * @param pageSize the page size
     * @return the liked artists
     * @throws ServiceException the service exception
     */
    PageSearchResult<Artist> getLikedArtists(long userId, int page, int pageSize) throws ServiceException;

    /**
     * Is liked artist boolean.
     *
     * @param userId   the user id
     * @param artistId the artist id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean isLikedArtist(long userId, long artistId) throws ServiceException;

    /**
     * Mark liked artist.
     *
     * @param userId   the user id
     * @param artistId the artist id
     * @throws ServiceException the service exception
     */
    void markLikedArtist(long userId, long artistId) throws ServiceException;

    /**
     * Unmark liked artist.
     *
     * @param userId   the user id
     * @param artistId the artist id
     * @throws ServiceException the service exception
     */
    void unmarkLikedArtist(long userId, long artistId) throws ServiceException;


    /**
     * Count playlists long.
     *
     * @param userId the user id
     * @return the long
     * @throws ServiceException the service exception
     */
    long countPlaylists(long userId) throws ServiceException;

    /**
     * Gets playlists.
     *
     * @param userId   the user id
     * @param page     the page
     * @param pageSize the page size
     * @return the playlists
     * @throws ServiceException the service exception
     */
    PageSearchResult<Playlist> getPlaylists(long userId, int page, int pageSize) throws ServiceException;

    /**
     * Has playlist boolean.
     *
     * @param userId     the user id
     * @param playlistId the playlist id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean hasPlaylist(long userId, long playlistId) throws ServiceException;
}
