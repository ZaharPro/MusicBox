package com.epam.musicbox.util.validator.impl;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ValidatorImplTest {

    private ValidatorImpl validator;

    @BeforeClass
    public void setUp() {
        validator = ValidatorImpl.getInstance();
    }

    @Test(groups = {"login"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "invalidLoginProvider")
    public void isInvalidLogin(String login) {
        assertFalse(validator.isValidLogin(login));
    }

    @Test(groups = {"login"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "validLoginProvider")
    public void isValidLogin(String login) {
        assertTrue(validator.isValidLogin(login));
    }

    @Test(groups = {"password"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "invalidPasswordProvider")
    public void isInvalidPassword(String password) {
        assertFalse(validator.isValidPassword(password));
    }

    @Test(groups = {"password"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "validPasswordProvider")
    public void isValidPassword(String password) {
        assertTrue(validator.isValidPassword(password));
    }

    @Test(groups = {"email"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "invalidEmailProvider")
    public void isInvalidEmail(String email) {
        assertFalse(validator.isValidEmail(email));
    }

    @Test(groups = {"email"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "validEmailProvider")
    public void isValidEmail(String email) {
        assertTrue(validator.isValidEmail(email));
    }

    @Test(groups = {"name"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "invalidNameProvider")
    public void isInvalidName(String name) {
        assertFalse(validator.isValidName(name));
    }

    @Test(groups = {"name"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "validNameProvider")
    public void isValidName(String name) {
        assertTrue(validator.isValidName(name));
    }

    @Test(groups = {"audioFileName"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "invalidAudioFileNameProvider")
    public void isInvalidAudioFileName(String audioFileName) {
        assertFalse(validator.isValidAudioFileName(audioFileName));
    }

    @Test(groups = {"audioFileName"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "validAudioFileNameProvider")
    public void isValidAudioFileName(String audioFileName) {
        assertTrue(validator.isValidAudioFileName(audioFileName));
    }

    @Test(groups = {"imageFileName"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "invalidImageFileNameProvider")
    public void isInvalidImageFileName(String imageFileName) {
        assertFalse(validator.isValidImageFileName(imageFileName));
    }

    @Test(groups = {"imageFileName"}, dataProviderClass = ValidatorImplProvider.class, dataProvider = "validImageFileNameProvider")
    public void isValidImageFileName(String imageFileName) {
        assertTrue(validator.isValidImageFileName(imageFileName));
    }
}
