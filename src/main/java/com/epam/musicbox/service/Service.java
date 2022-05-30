package com.epam.musicbox.service;

import com.epam.musicbox.exception.HttpException;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    int PAGE_SIZE = 20;

    List<T> findPage(int page) throws HttpException;

    Optional<T> findById(Long id);

    void save(T t) throws HttpException;

    void deleteById(Long id) throws HttpException;

    static int getOffset(int page) {
        return PAGE_SIZE * page;
    }
}