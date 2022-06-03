package com.epam.musicbox.repository;

import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;

import java.util.List;

public interface ArtistRepository extends Repository<Artist> {
    List<Artist> findByName(String regex, int offset, int limit) throws RepositoryException;

    List<Track> getTracks(Long artistId, int offset, int limit) throws RepositoryException;

    void addTrack(Long artistId, Long trackId) throws RepositoryException;

    void removeTrack(Long artistId, Long trackId) throws RepositoryException;
}
