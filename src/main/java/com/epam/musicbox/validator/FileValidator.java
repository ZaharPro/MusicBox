package com.epam.musicbox.validator;

public interface FileValidator {

    boolean isValidAudioFileName(String fileName);

    boolean isValidImageFileName(String fileName);

    boolean isValidKey(String key);
}
