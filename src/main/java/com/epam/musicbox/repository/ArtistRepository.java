package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends Repository<Artist> {
    Optional<Artist> findByName(String name);

    List<Track> getTracks(Integer artistId, int offset, int limit) throws HttpException;

    void addTrack(Integer playlistId, Integer trackId) throws HttpException;

    void removeTrack(Integer playlistId, Integer trackId) throws HttpException;
}
