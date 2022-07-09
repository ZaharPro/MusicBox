package com.epam.musicbox.service;

import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * The interface File service.
 */
public interface FileService {

    /**
     * The constant UPLOAD_DIR.
     */
    String UPLOAD_DIR = "file" + File.separatorChar;
    /**
     * The constant IMAGE_DIR.
     */
    String IMAGE_DIR = "img" + File.separatorChar;
    /**
     * The constant AUDIO_DIR.
     */
    String AUDIO_DIR = "audio" + File.separatorChar;

    /**
     * Save string.
     *
     * @param root the root
     * @param key  the key
     * @param part the part
     * @return the string
     * @throws ServiceException the service exception
     */
    String save(String root, String key, Part part) throws ServiceException;

    /**
     * Get optional.
     *
     * @param root the root
     * @param key  the key
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Path> get(String root, String key) throws ServiceException;

    /**
     * Remove.
     *
     * @param root the root
     * @param key  the key
     * @throws ServiceException the service exception
     */
    void remove(String root, String key) throws ServiceException;

    /**
     * Gets upload dir.
     *
     * @param req the http request
     * @return the upload dir
     */
    static String getUploadDir(HttpServletRequest req) {
        return req.getServletContext().getRealPath("") + UPLOAD_DIR;
    }
}
