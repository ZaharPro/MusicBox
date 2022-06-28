package com.epam.musicbox.service.impl;

import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthServiceImplTest {

    private static final AuthServiceImpl authService = AuthServiceImpl.getInstance();

    @Test
    public void generateTokenIfNullNull() {
        Assertions.assertNotNull(authService.generateToken(null));
    }


    @Test
    public void getTokenIfNullDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> authService.getToken(null));
    }

    @Test
    public void getTokenIfNullNull() throws ServiceException {
        Jws<Claims> token = authService.getToken(null);
        Assertions.assertNull(token);
    }


    @Test
    public void signUpIfNullExceptionThrown() {
        Assertions.assertThrows(ServiceException.class, () -> {
            authService.signUp(null, null, null);
        });
    }

    @Test
    public void loginIfNullExceptionThrown() {
        Assertions.assertThrows(ServiceException.class, () -> {
            authService.login(null, null);
        });
    }

    @Test
    public void changePasswordIfNullExceptionThrown() {
        Assertions.assertThrows(ServiceException.class, () -> {
            authService.changePassword(-1, null, null);
        });
    }
}
