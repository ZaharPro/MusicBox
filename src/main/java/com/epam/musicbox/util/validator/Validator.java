package com.epam.musicbox.util.validator;

/**
 * The interface Validator.
 */
public interface Validator {

    /**
     * Is valid login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean isValidLogin(String login);

    /**
     * Is valid password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean isValidPassword(String password);

    /**
     * Is valid email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean isValidEmail(String email);

    /**
     * Is valid name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean isValidName(String name);

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
