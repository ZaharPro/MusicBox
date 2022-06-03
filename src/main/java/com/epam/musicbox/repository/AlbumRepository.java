package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.RepositoryException;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface AlbumRepository extends Repository<Album> {
    List<Album> findByName(String regex, int offset, int limit) throws RepositoryException;
}
