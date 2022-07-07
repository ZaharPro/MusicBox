package com.epam.musicbox.service;

import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Predicate;

/**
 * The interface File service.
 */
public interface FileService {
    /**
     * Put string.
     *
     * @param req               the http request
     * @param key               the key
     * @param file              the file
     * @param required          the required
     * @param dir               the dir
     * @param fileNameValidator the file name validator
     * @return the string
     * @throws ServiceException the service exception
     */
    String put(HttpServletRequest req,
               String key,
               String file,
               boolean required,
               String dir,
               Predicate<String> fileNameValidator) throws ServiceException;

    /**
     * Remove.
     *
     * @param req the http request
     * @param key the key
     * @throws ServiceException the service exception
     */
    void remove(HttpServletRequest req, String key) throws ServiceException;
}
