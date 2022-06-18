package com.epam.musicbox.validator;

public interface Validator {

    boolean isValidLogin(String login);

    boolean isValidPassword(String password);

    boolean isValidEmail(String email);

    boolean isValidName(String name);

    boolean isValidAudioFileName(String fileName);

    boolean isValidImageFileName(String fileName);

    boolean isValidKey(String key);
}
