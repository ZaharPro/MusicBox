package com.epam.musicbox.service;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.page.PageSearchResult;

import java.util.Optional;

public interface EntityService<T extends Entity> {

    long count() throws ServiceException;

    PageSearchResult<T> findPage(int page, int pageSize) throws ServiceException;

    Optional<T> findById(long id) throws ServiceException;

    long save(T t) throws ServiceException;

    void deleteById(long id) throws ServiceException;
}