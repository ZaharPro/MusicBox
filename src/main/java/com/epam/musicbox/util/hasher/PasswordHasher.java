package com.epam.musicbox.util.hasher;

public interface PasswordHasher {

    String hash(String password);

    boolean checkPassword(String password, String token);
}
