package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends Repository<Artist> {
    Optional<Artist> findByName(String name);

    List<Track> getTracks(Long artistId, int offset, int limit) throws HttpException;

    void addTrack(Long playlistId, Long trackId) throws HttpException;

    void removeTrack(Long playlistId, Long trackId) throws HttpException;
}
