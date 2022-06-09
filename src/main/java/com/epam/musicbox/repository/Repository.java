package com.epam.musicbox.repository;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {
    List<T> findAll(int offset, int limit) throws RepositoryException;

    Optional<T> findById(long id);

    long save(T t) throws RepositoryException;

    void deleteById(long id) throws RepositoryException;
}