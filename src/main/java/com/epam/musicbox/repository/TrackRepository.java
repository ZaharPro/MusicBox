package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Track;

import java.util.Optional;

public interface TrackRepository extends Repository<Track> {
    Optional<Track> findByName(String name);
}
