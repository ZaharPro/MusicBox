package com.epam.musicbox.validator;

public interface FileValidator {

    boolean isValidKey(String key);

    boolean isValidAudioFileName(String fileName);

    boolean isValidImageFileName(String fileName);
}
