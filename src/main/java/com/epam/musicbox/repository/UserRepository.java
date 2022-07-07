package com.epam.musicbox.repository;

import com.epam.musicbox.entity.*;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends Repository<User> {

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws RepositoryException the repository exception
     */
    Optional<User> findByLogin(String login) throws RepositoryException;

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws RepositoryException the repository exception
     */
    Optional<User> findByEmail(String email) throws RepositoryException;


    /**
     * Count by role long.
     *
     * @param roleId the role id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countByRole(int roleId) throws RepositoryException;

    /**
     * Find by role list.
     *
     * @param roleId the role id
     * @param offset the offset
     * @param limit  the limit
     * @return the list
     * @throws RepositoryException the repository exception
     */
    List<User> findByRole(int roleId, int offset, int limit) throws RepositoryException;

    /**
     * Sets role.
     *
     * @param userId the user id
     * @param roleId the role id
     * @throws RepositoryException the repository exception
     */
    void setRole(long userId, int roleId) throws RepositoryException;


    /**
     * Count liked tracks long.
     *
     * @param userId the user id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countLikedTracks(long userId) throws RepositoryException;

    /**
     * Gets liked tracks.
     *
     * @param userId the user id
     * @param offset the offset
     * @param limit  the limit
     * @return the liked tracks
     * @throws RepositoryException the repository exception
     */
    List<Track> getLikedTracks(long userId, int offset, int limit) throws RepositoryException;

    /**
     * Is liked track boolean.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean isLikedTrack(long userId, long trackId) throws RepositoryException;

    /**
     * Mark liked track.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @throws RepositoryException the repository exception
     */
    void markLikedTrack(long userId, long trackId) throws RepositoryException;

    /**
     * Unmark liked track.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @throws RepositoryException the repository exception
     */
    void unmarkLikedTrack(long userId, long trackId) throws RepositoryException;


    /**
     * Count liked albums long.
     *
     * @param userId the user id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countLikedAlbums(long userId) throws RepositoryException;

    /**
     * Gets liked albums.
     *
     * @param userId the user id
     * @param offset the offset
     * @param limit  the limit
     * @return the liked albums
     * @throws RepositoryException the repository exception
     */
    List<Album> getLikedAlbums(long userId, int offset, int limit) throws RepositoryException;

    /**
     * Is liked album boolean.
     *
     * @param userId  the user id
     * @param albumId the album id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean isLikedAlbum(long userId, long albumId) throws RepositoryException;

    /**
     * Mark liked album.
     *
     * @param userId  the user id
     * @param albumId the album id
     * @throws RepositoryException the repository exception
     */
    void markLikedAlbum(long userId, long albumId) throws RepositoryException;

    /**
     * Unmark liked album.
     *
     * @param userId  the user id
     * @param albumId the album id
     * @throws RepositoryException the repository exception
     */
    void unmarkLikedAlbum(long userId, long albumId) throws RepositoryException;


    /**
     * Count liked artists long.
     *
     * @param userId the user id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countLikedArtists(long userId) throws RepositoryException;

    /**
     * Gets liked artists.
     *
     * @param userId the user id
     * @param offset the offset
     * @param limit  the limit
     * @return the liked artists
     * @throws RepositoryException the repository exception
     */
    List<Artist> getLikedArtists(long userId, int offset, int limit) throws RepositoryException;

    /**
     * Is liked artist boolean.
     *
     * @param userId   the user id
     * @param artistId the artist id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean isLikedArtist(long userId, long artistId) throws RepositoryException;

    /**
     * Mark liked artist.
     *
     * @param userId   the user id
     * @param artistId the artist id
     * @throws RepositoryException the repository exception
     */
    void markLikedArtist(long userId, long artistId) throws RepositoryException;

    /**
     * Unmark liked artist.
     *
     * @param userId   the user id
     * @param artistId the artist id
     * @throws RepositoryException the repository exception
     */
    void unmarkLikedArtist(long userId, long artistId) throws RepositoryException;


    /**
     * Count playlists long.
     *
     * @param userId the user id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countPlaylists(long userId) throws RepositoryException;

    /**
     * Gets playlists.
     *
     * @param userId the user id
     * @param offset the offset
     * @param limit  the limit
     * @return the playlists
     * @throws RepositoryException the repository exception
     */
    List<Playlist> getPlaylists(long userId, int offset, int limit) throws RepositoryException;

    /**
     * Has playlist boolean.
     *
     * @param userId     the user id
     * @param playlistId the playlist id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean hasPlaylist(long userId, long playlistId) throws RepositoryException;
}
