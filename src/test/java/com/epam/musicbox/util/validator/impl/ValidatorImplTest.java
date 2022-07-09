package com.epam.musicbox.util.validator.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorImplTest {

    private static final String NULL = null;
    private static final String EMPTY = "";
    private static final String BAD_STR = "\r\t\n\f!@#$%^&*(){}[]";

    private static final String BAD_LOGIN = BAD_STR;
    private static final String GOOD_LOGIN = "login";

    private static final String BAD_PASSWORD = "\n\t\n\f";
    private static final String GOOD_PASSWORD = "qwer1234@$!%*#?&";

    private static final String BAD_EMAIL = BAD_STR;
    private static final String GOOD_EMAIL = "1234qwer@gmail.com";

    private static final String BAD_NAME = BAD_STR;
    private static final String GOOD_NAME = "Track (Remix[Version 12-34])";

    private static final String BAD_IMAGE = BAD_STR;
    private static final String GOOD_IMAGE = "1234qwer.png";

    private static final String BAD_AUDIO = BAD_STR;
    private static final String GOOD_AUDIO = "1234qwer.mp3";

    private static final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Test
    public void isValidLoginIfNullFalse() {
        Assertions.assertFalse(validator.isValidLogin(NULL));
    }

    @Test
    public void isValidLoginIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidLogin(EMPTY));
    }

    @Test
    public void isValidLoginIfBadFalse() {
        Assertions.assertFalse(validator.isValidLogin(BAD_LOGIN));
    }

    @Test
    public void isValidLoginIfGoodTrue() {
        Assertions.assertTrue(validator.isValidLogin(GOOD_LOGIN));
    }


    @Test
    public void isValidPasswordIfNullFalse() {
        Assertions.assertFalse(validator.isValidPassword(NULL));
    }

    @Test
    public void isValidPasswordIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidPassword(EMPTY));
    }

    @Test
    public void isValidPasswordIfBadFalse() {
        Assertions.assertFalse(validator.isValidPassword(BAD_PASSWORD));
    }

    @Test
    public void isValidPasswordIfGoodTrue() {
        Assertions.assertTrue(validator.isValidPassword(GOOD_PASSWORD));
    }


    @Test
    public void isValidEmailIfNullFalse() {
        Assertions.assertFalse(validator.isValidEmail(NULL));
    }

    @Test
    public void isValidEmailIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidEmail(EMPTY));
    }

    @Test
    public void isValidEmailIfBadFalse() {
        Assertions.assertFalse(validator.isValidEmail(BAD_EMAIL));
    }

    @Test
    public void isValidEmailIfGoodTrue() {
        Assertions.assertTrue(validator.isValidEmail(GOOD_EMAIL));
    }


    @Test
    public void isValidNameIfNullFalse() {
        Assertions.assertFalse(validator.isValidName(NULL));
    }

    @Test
    public void isValidNameIfEmptyFalse() {
        Assertions.assertFalse(validator.isValidName(EMPTY));
    }

    @Test
    public void isValidNameIfBadFalse() {
        Assertions.assertFalse(validator.isValidName(BAD_NAME));
    }

    @Test
    public void isValidNameIfGoodTrue() {
        Assertions.assertTrue(validator.isValidName(GOOD_NAME));
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
