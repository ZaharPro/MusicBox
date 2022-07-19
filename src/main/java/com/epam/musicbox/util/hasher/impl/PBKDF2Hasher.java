package com.epam.musicbox.util.hasher.impl;

import com.epam.musicbox.util.hasher.Hasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class PBKDF2Hasher implements Hasher {

    private static final Logger logger = LogManager.getLogger();

    private static final PBKDF2Hasher instance = new PBKDF2Hasher();

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;
    private static final int SALT_LENGTH = KEY_LENGTH / 8;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final SecureRandom random = new SecureRandom();
    private static final SecretKeyFactory factory;

    private static final String ALGORITHM_NOT_FOUND = "Algorithm not found";
    private static final String INVALID_KEY_SPEC = "Invalid key specification";

    private PBKDF2Hasher() {
    }

    static {
        try {
            factory = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(ALGORITHM_NOT_FOUND, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static PBKDF2Hasher getInstance() {
        return instance;
    }

    public String hash(char[] plain) {
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(plain, salt);
        byte[] hash = new byte[salt.length + dk.length]; //salt + hash
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }

    public boolean checkPassword(char[] plain, String hash) {
        if (hash == null) {
            return false;
        }
        byte[] hashBytes;
        try {
            hashBytes = Base64.getUrlDecoder().decode(hash);
        } catch (IllegalArgumentException e) {
            logger.error(e);
            return false;
        }
        byte[] salt = Arrays.copyOfRange(hashBytes, 0, SALT_LENGTH);
        byte[] check = pbkdf2(plain, salt);
        return Arrays.equals(hashBytes, salt.length, hashBytes.length, check, 0, check.length);
    }

    private static byte[] pbkdf2(char[] plain, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(plain, salt, ITERATIONS, KEY_LENGTH);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            logger.warn(INVALID_KEY_SPEC, e);
            throw new AssertionError(e);
        }
    }

    @Override
    public String hash(String plain) {
        return hash(plain == null ? null : plain.toCharArray());
    }

    @Override
    public boolean check(String plain, String hash) {
        return checkPassword(plain == null ? null : plain.toCharArray(), hash);
    }
}
