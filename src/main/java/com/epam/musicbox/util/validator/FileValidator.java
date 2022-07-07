package com.epam.musicbox.util.validator;

/**
 * The interface File validator.
 */
public interface FileValidator {

    /**
     * Is valid key boolean.
     *
     * @param key the key
     * @return the boolean
     */
    boolean isValidKey(String key);

    /**
     * Is valid audio file name boolean.
     *
     * @param fileName the file name
     * @return the boolean
     */
    boolean isValidAudioFileName(String fileName);

    /**
     * Is valid image file name boolean.
     *
     * @param fileName the file name
     * @return the boolean
     */
    boolean isValidImageFileName(String fileName);
}
