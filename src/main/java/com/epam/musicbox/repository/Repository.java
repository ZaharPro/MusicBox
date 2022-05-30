package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll(int offset, int limit) throws HttpException;

    Optional<T> findById(Long id);

    void save(T t) throws HttpException;

    void deleteById(Long id) throws HttpException;
}