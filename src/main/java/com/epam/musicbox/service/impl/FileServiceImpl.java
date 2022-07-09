package com.epam.musicbox.service.impl;

import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileServiceImpl implements FileService {

    private static final String ALGORITHM = "SHA-256";

    private static final FileServiceImpl instance = new FileServiceImpl();

    private final MessageDigest messageDigest;
    private final Lock messageDigestLock;

    private FileServiceImpl() {
        try {
            messageDigest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        }
        messageDigestLock = new ReentrantLock();
    }

    public static FileServiceImpl getInstance() {
        return instance;
    }

    @Override
    public String save(String root, String key, Part part) throws ServiceException {
        try {
            remove(root, key);
            Path rootPath = Paths.get(root);
            Files.createDirectories(rootPath);
            String fileName = fileName(hash(key), part.getSubmittedFileName());
            part.write(root + fileName);
            return fileName;
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Path> get(String root, String key) throws ServiceException {
        try {
            String hash = hash(key);
            Path rootPath = Paths.get(root);
            if (Files.exists(rootPath)) {
                return Files.walk(rootPath)
                        .filter(path -> hash.equals(extractKey(getFileName(path))))
                        .findAny();
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void remove(String root, String key) throws ServiceException {
        Optional<Path> path = get(root, key);
        if (path.isPresent()) {
            try {
                Files.delete(path.get());
            } catch (IOException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
    }

    private String hash(String key) {
        try {
            messageDigestLock.lock();
            byte[] hash = messageDigest.digest(key.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } finally {
            messageDigestLock.unlock();
        }
    }

    private String fileName(String key, String file) {
        return key + getExtension(file);
    }

    private String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        return i == -1 ? "" : fileName.substring(i);
    }

    private String extractKey(String fileName) {
        int i = fileName.indexOf('.');
        return i == -1 ? fileName : fileName.substring(0, i);
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}