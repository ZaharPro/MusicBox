package com.epam.musicbox.repository;

import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll(int offset, int limit) throws HttpException;

    Optional<T> findById(Integer id) throws HttpException;

    void save(T t) throws HttpException;

    void deleteById(Integer id) throws HttpException;
}