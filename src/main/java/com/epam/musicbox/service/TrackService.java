package com.epam.musicbox.service;

import com.epam.musicbox.entity.Track;

import java.util.Optional;

public interface TrackService extends Service<Track> {
    Optional<Track> findByName(String name);
}
