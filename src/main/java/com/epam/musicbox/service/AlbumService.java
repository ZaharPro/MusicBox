package com.epam.musicbox.service;

import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

public interface AlbumService extends EntityService<Album> {

    long countByName(String regex) throws ServiceException;

    PageSearchResult<Album> findByName(String name, int page, int pageSize) throws ServiceException;
}
