package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;

import java.util.List;

public interface ArtistRepository extends Repository<Artist> {
    List<Track> getTracks(Integer artistId, int offset, int limit) throws HttpException;
}
