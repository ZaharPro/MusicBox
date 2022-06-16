package com.epam.musicbox.service;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.psr.PageSearchResult;

public interface TrackService extends EntityService<Track> {

    long countByName(String name) throws ServiceException;

    PageSearchResult<Track> findByName(String name, int page, int pageSize) throws ServiceException;
}
