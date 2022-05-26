package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;

import java.util.Optional;

public interface AlbumService extends Service<Album> {
    Optional<Album> findByName(String name);
}
