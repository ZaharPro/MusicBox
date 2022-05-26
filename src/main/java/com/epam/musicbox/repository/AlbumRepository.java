package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;

import java.util.Optional;

public interface AlbumRepository extends Repository<Album> {
    Optional<Album> findByName(String name);
}
