package com.epam.musicbox.service;

import com.epam.musicbox.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    List<T> findPage(int page) throws ServiceException;

    Optional<T> findById(long id);

    long save(T t) throws ServiceException;

    void deleteById(long id) throws ServiceException;
}