package com.epam.musicbox.util.hasher;


/**
 * The interface Hasher.
 */
public interface Hasher {

    /**
     * Hash string.
     *
     * @param plain the plain to hash
     * @return the hash function result
     */
    String hash(String plain);

    /**
     * Check boolean.
     *
     * @param plain the plain to check
     * @param hash  the hash
     * @return the boolean
     */
    boolean check(String plain, String hash);
}
