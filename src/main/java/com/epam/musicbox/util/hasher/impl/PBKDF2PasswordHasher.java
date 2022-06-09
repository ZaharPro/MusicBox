package com.epam.musicbox.util.hasher.impl;

import com.epam.musicbox.util.hasher.PasswordHasher;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class PBKDF2PasswordHasher implements PasswordHasher {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 128;
    private static final int SALT_LENGTH = KEY_LENGTH / 8;
    private static final SecureRandom random = new SecureRandom();
    
    private static final PBKDF2PasswordHasher instance = new PBKDF2PasswordHasher();

    private PBKDF2PasswordHasher() {
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
        return Arrays.equals(hash, salt.length, hash.length,
                check, 0, check.length);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt) {
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, ex);
        } catch (InvalidKeySpecException ex) {
            throw new IllegalStateException("Invalid SecretKeyFactory", ex);
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
