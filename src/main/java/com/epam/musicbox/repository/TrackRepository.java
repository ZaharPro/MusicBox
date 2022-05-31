package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface TrackRepository extends Repository<Track> {
    List<Track> findByName(String regex, int offset, int limit) throws HttpException;
}
