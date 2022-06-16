package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;

public interface ArtistRepository extends Repository<Artist> {

    long countByName(String regex) throws RepositoryException;

    List<Artist> findByName(String regex, int offset, int limit) throws RepositoryException;


    long countTracks(long artistId) throws RepositoryException;

    List<Track> getTracks(long artistId, int offset, int limit) throws RepositoryException;

    boolean hasTrack(long artistId, long trackId) throws RepositoryException;

    void addTrack(long artistId, long trackId) throws RepositoryException;

    void removeTrack(long artistId, long trackId) throws RepositoryException;


    long countAlbums(long artistId) throws RepositoryException;

    List<Album> getAlbums(long artistId, int offset, int limit) throws RepositoryException;
}
