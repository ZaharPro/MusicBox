package com.epam.musicbox.util.validator.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileValidatorImplTest {

    private static final String NULL = null;
    private static final String EMPTY = "";
    private static final String BAD_STR = "\r\t\n\f!@#$%^&*(){}[]";

    private static final String BAD_KEY = BAD_STR;
    private static final String GOOD_KEY = "1234-qwer";

    private static final String BAD_IMAGE = BAD_STR;
    private static final String GOOD_IMAGE = "1234qwer.png";

    private static final String BAD_AUDIO = BAD_STR;
    private static final String GOOD_AUDIO = "1234qwer.mp3";

    private static final FileValidatorImpl validator = FileValidatorImpl.getInstance();

    @Test
    public void isValidKeyIfNullFalse() {
        Assertions.assertFalse(validator.isValidKey(NULL));
    }

    @Test
    public void isValidKeyIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidKey(EMPTY));
    }

    @Test
    public void isValidKeyIfBadFalse() {
        Assertions.assertFalse(validator.isValidKey(BAD_KEY));
    }

    @Test
    public void isValidKeyIfGoodTrue() {
        Assertions.assertTrue(validator.isValidKey(GOOD_KEY));
    }

    @Test
    public void isValidImageFileNameIfNullFalse() {
        Assertions.assertFalse(validator.isValidImageFileName(NULL));
    }

    @Test
    public void isValidImageFileNameIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidImageFileName(EMPTY));
    }

    @Test
    public void isValidImageFileNameIfBadFalse() {
        Assertions.assertFalse(validator.isValidImageFileName(BAD_IMAGE));
    }

    @Test
    public void isValidImageFileNameIfGoodTrue() {
        Assertions.assertTrue(validator.isValidImageFileName(GOOD_IMAGE));
    }

    @Test
    public void isValidAudioFileNameIfNullFalse() {
        Assertions.assertFalse(validator.isValidAudioFileName(NULL));
    }

    @Test
    public void isValidAudioFileNameIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidAudioFileName(EMPTY));
    }

    @Test
    public void isValidAudioFileNameIfBadFalse() {
        Assertions.assertFalse(validator.isValidAudioFileName(BAD_AUDIO));
    }

    @Test
    public void isValidAudioFileNameIfGoodTrue() {
        Assertions.assertTrue(validator.isValidAudioFileName(GOOD_AUDIO));
    }
}
