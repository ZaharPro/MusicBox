package com.epam.musicbox.service;

import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;

import java.util.List;

public interface TrackService extends Service<Track> {
    List<Track> findByName(String name, int page) throws ServiceException;
}
