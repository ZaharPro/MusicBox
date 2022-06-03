package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface AlbumService extends Service<Album> {
    List<Album> findByName(String name, int page) throws ServiceException;
}
