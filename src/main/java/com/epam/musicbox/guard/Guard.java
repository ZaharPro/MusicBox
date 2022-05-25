package com.epam.musicbox.guard;

import com.epam.musicbox.exception.HttpException;

public interface Guard {
    void protect() throws HttpException;
}
