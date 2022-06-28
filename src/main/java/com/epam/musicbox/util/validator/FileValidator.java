package com.epam.musicbox.util.validator;

public interface FileValidator {

    boolean isValidKey(String key);

    boolean isValidAudioFileName(String fileName);

    boolean isValidImageFileName(String fileName);
}
