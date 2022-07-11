package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

/**
 * The interface Artist repository.
 */
public interface ArtistRepository extends Repository<Artist> {

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
    List<Artist> findByName(String name, int offset, int limit) throws RepositoryException;


    /**
     * Count tracks long.
     *
     * @param artistId the artist id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countTracks(long artistId) throws RepositoryException;

    /**
     * Gets tracks.
     *
     * @param artistId the artist id
     * @param offset   the offset
     * @param limit    the limit
     * @return the tracks
     * @throws RepositoryException the repository exception
     */
    List<Track> getTracks(long artistId, int offset, int limit) throws RepositoryException;

    /**
     * Has track boolean.
     *
     * @param artistId the artist id
     * @param trackId  the track id
     * @return the boolean
     * @throws RepositoryException the repository exception
     */
    boolean hasTrack(long artistId, long trackId) throws RepositoryException;

    /**
     * Add track.
     *
     * @param artistId the artist id
     * @param trackId  the track id
     * @throws RepositoryException the repository exception
     */
    void addTrack(long artistId, long trackId) throws RepositoryException;

    /**
     * Remove track.
     *
     * @param artistId the artist id
     * @param trackId  the track id
     * @throws RepositoryException the repository exception
     */
    void removeTrack(long artistId, long trackId) throws RepositoryException;


    /**
     * Count albums long.
     *
     * @param artistId the artist id
     * @return the long
     * @throws RepositoryException the repository exception
     */
    long countAlbums(long artistId) throws RepositoryException;

    /**
     * Gets albums.
     *
     * @param artistId the artist id
     * @param offset   the offset
     * @param limit    the limit
     * @return the albums
     * @throws RepositoryException the repository exception
     */
    List<Album> getAlbums(long artistId, int offset, int limit) throws RepositoryException;
}
