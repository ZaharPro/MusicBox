package com.epam.musicbox.repository;

import com.epam.musicbox.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll(int offset, int limit) throws RepositoryException;

    Optional<T> findById(Long id);

    long save(T t) throws RepositoryException;

    void deleteById(Long id) throws RepositoryException;
}