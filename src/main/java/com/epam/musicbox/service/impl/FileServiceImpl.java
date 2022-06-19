package com.epam.musicbox.service.impl;

import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class FileServiceImpl implements FileService {

    private static final Logger logger = LogManager.getLogger();

    private static final String INVALID_KEY_MSG = "Invalid key: ";
    private static final String INVALID_FILE_NAME_MSG = "Invalid file name: ";
    private static final String KEY_NOT_FOUND_MSG = "Key not found";
    private static final String FILE_EXTENSION_NOT_FOUND = "File extension not found";

    private static final String UPLOAD_ROOT_DIR = "file";
    private static final String KEY_SEPARATOR = "_";

    private static final FileServiceImpl instance = new FileServiceImpl();

    private final Validator validator = ValidatorImpl.getInstance();

    private FileServiceImpl() {
    }

    public static FileServiceImpl getInstance() {
        return instance;
    }

    public static String generateKey(String entityField, Object id) {
        return entityField + id;
    }

    private String getRoot(HttpServletRequest req) {
        return req.getServletContext().getRealPath("") + UPLOAD_ROOT_DIR + File.separatorChar;
    }

    private void remove(String root, String key) throws IOException {
        Optional<Path> oldPath = filePathByKey(root, key);
        if (oldPath.isPresent()) {
            Files.delete(oldPath.get());
        }
    }

    private static String buildPath(String root, String dir, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(root);
        if (dir != null) {
            sb.append(dir).append(File.separatorChar);
        }
        sb.append(fileName);
        return sb.toString();
    }

    private static String getExtension(String fileName) throws ServiceException {
        int i = fileName.lastIndexOf('.');
        if (i == -1) {
            throw new ServiceException(FILE_EXTENSION_NOT_FOUND);
        }
        return fileName.substring(i);
    }

    private static Optional<Path> filePathByKey(String root, String key) throws IOException {
        return Files.walk(Paths.get(root))
                .filter(Files::isRegularFile)
                .filter(path -> key.equals(extractKey(getFileName(path))))
                .findAny();
    }

    private static String extractKey(String fileName) {
        String[] parts = fileName.split(KEY_SEPARATOR);
        if (parts.length == 0) {
            logger.error(KEY_NOT_FOUND_MSG + fileName);
            return null;
        }
        return parts[0];
    }

    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }

    @Override
    public String put(HttpServletRequest req,
                      String key,
                      String file,
                      boolean required,
                      String dir,
                      Predicate<String> fileNameValidator) throws ServiceException {
        if (!validator.isValidKey(key)) {
            throw new ServiceException(INVALID_KEY_MSG + key);
        }
        try {
            Part filePart = req.getPart(file);
            String fileName = filePart.getSubmittedFileName();

            String root = getRoot(req);
            if (fileName.isEmpty() && !required) {
                return filePathByKey(root, key)
                        .map(FileServiceImpl::getFileName)
                        .orElse(null);
            }
            if (fileNameValidator != null && !fileNameValidator.test(fileName)) {
                throw new ServiceException(INVALID_FILE_NAME_MSG + fileName);
            }

            remove(root, key);

            String newFileName = key + KEY_SEPARATOR + UUID.randomUUID() + getExtension(fileName);
            String path = buildPath(root, dir, newFileName);

            Files.createDirectories(Paths.get(path).getParent());
            for (Part part : req.getParts()) {
                if (fileName.equals(part.getSubmittedFileName())) {
                    part.write(path);
                }
            }

            return newFileName;
        } catch (ServletException | IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void remove(HttpServletRequest req, String key) throws ServiceException {
        try {
            String root = getRoot(req);
            remove(root, key);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}
