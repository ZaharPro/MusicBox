package com.epam.musicbox.guard;

import com.epam.musicbox.exception.HttpException;

public interface Guard {
    void protect() throws HttpException;

    default Guard concat(Guard guard) {
        return () -> {
            protect();
            guard.protect();
        };
    }
}
