package com.epam.musicbox.util.validator.impl;

import com.epam.musicbox.util.validator.FileValidator;

import java.util.regex.Pattern;

public class FileValidatorImpl implements FileValidator {

    private static final String KEY_REGEX = "^([\\w\\d-]{4,32})$";
    private static final String AUDIO_FILE_NAME_REGEX = ".*\\.(?:wav|mp3)$";
    private static final String IMAGE_FILE_NAME_REGEX = ".*\\.(gif|jpe?g|png)$";

    private static final FileValidatorImpl instance = new FileValidatorImpl();

    private final Pattern keyPattern = Pattern.compile(KEY_REGEX);
    private final Pattern audioFileNamePattern = Pattern.compile(AUDIO_FILE_NAME_REGEX);
    private final Pattern imageFileNamePattern = Pattern.compile(IMAGE_FILE_NAME_REGEX);

    private FileValidatorImpl() {
    }

    public static FileValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isValidKey(String key) {
        return key != null &&
               keyPattern.matcher(key).matches();
    }

    @Override
    public boolean isValidAudioFileName(String fileName) {
        return fileName != null &&
               audioFileNamePattern.matcher(fileName).matches();
    }

    @Override
    public boolean isValidImageFileName(String fileName) {
        return fileName != null &&
               imageFileNamePattern.matcher(fileName).matches();
    }
}
