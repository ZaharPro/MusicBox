package com.epam.musicbox.service.impl;

import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthServiceImplTest {

    private static final AuthServiceImpl authService = AuthServiceImpl.getInstance();

    private static <T> T getNull() {
        return null;
    }

    @Test
    public void generateTokenIfNullNull() {
        Assertions.assertNotNull(authService.generateToken(getNull()));
    }

    @Test
    public void getTokenIfNullNull() {
        Jws<Claims> token = Assertions.assertDoesNotThrow(() -> authService.getToken(getNull()));
        Assertions.assertNull(token);
    }

    @Test
    public void signUpIfNullExceptionThrown() {
        Assertions.assertThrows(ServiceException.class, () -> {
            authService.signUp(getNull(), getNull(), getNull());
        });
    }

    @Test
    public void loginIfNullExceptionThrown() {
        Assertions.assertThrows(ServiceException.class, () -> {
            authService.login(getNull(), getNull());
        });
    }

    @Test
    public void changePasswordIfNullExceptionThrown() {
        Assertions.assertThrows(ServiceException.class, () -> {
            authService.changePassword(-1, getNull(), getNull());
        });
    }
}
