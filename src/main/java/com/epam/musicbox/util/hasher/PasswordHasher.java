package com.epam.musicbox.util.hasher;

/**
 * The interface Password hasher.
 */
public interface PasswordHasher {

    /**
     * Hash string.
     *
     * @param password the password
     * @return the string
     */
    String hash(String password);

    /**
     * Check password boolean.
     *
     * @param password the password
     * @param token    the token
     * @return the boolean
     */
    boolean checkPassword(String password, String token);
}
