package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;

import java.util.List;

public interface ArtistRepository extends Repository<Artist, Long> {
    List<Artist> findByName(String regex, int offset, int limit) throws RepositoryException;

    List<Track> getTracks(long artistId, int offset, int limit) throws RepositoryException;

    void addTrack(long artistId, long trackId) throws RepositoryException;

    void removeTrack(long artistId, long trackId) throws RepositoryException;

    List<Album> getAlbums(long artistId, int offset, int limit) throws RepositoryException;
}
