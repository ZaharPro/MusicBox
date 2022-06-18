package com.epam.musicbox.service;

import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Predicate;

public interface FileService {

    String IMG_DIR = "img";
    String AUDIO_DIR = "audio";

    String put(HttpServletRequest req,
               String key,
               String file,
               boolean required,
               String dir,
               Predicate<String> fileNameValidator) throws ServiceException;

    void remove(HttpServletRequest req, String key) throws ServiceException;
}
