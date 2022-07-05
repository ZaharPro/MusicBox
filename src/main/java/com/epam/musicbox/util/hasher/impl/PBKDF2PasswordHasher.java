package com.epam.musicbox.util.hasher.impl;

import com.epam.musicbox.util.hasher.PasswordHasher;
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

public class PBKDF2PasswordHasher implements PasswordHasher {

    private static final Logger logger = LogManager.getLogger();

    private static final PBKDF2PasswordHasher instance = new PBKDF2PasswordHasher();

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;
    private static final int SALT_LENGTH = KEY_LENGTH / 8;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final SecureRandom random = new SecureRandom();
    private static final SecretKeyFactory factory;

    private static final String ALGORITHM_NOT_FOUND = "Algorithm not found";
    private static final String INVALID_KEY_SPEC = "Invalid key specification";

    private PBKDF2PasswordHasher() {
    }

    static {
        try {
            factory = SecretKeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(ALGORITHM_NOT_FOUND, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static PBKDF2PasswordHasher getInstance() {
        return instance;
    }

    public String hash(char[] password) {
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt);
        byte[] hash = new byte[salt.length + dk.length]; //salt + hash
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(hash);
    }

    public boolean checkPassword(char[] password, String token) {
        byte[] hash = Base64.getUrlDecoder().decode(token);
        byte[] salt = Arrays.copyOfRange(hash, 0, SALT_LENGTH);
        byte[] check = pbkdf2(password, salt);
        return Arrays.equals(hash, salt.length, hash.length, check, 0, check.length);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            logger.fatal(INVALID_KEY_SPEC, e);
            throw new Error(e);
        }
    }

    @Override
    public String hash(String password) {
        return hash(password.toCharArray());
    }

    @Override
    public boolean checkPassword(String password, String token) {
        return checkPassword(password.toCharArray(), token);
    }
}
