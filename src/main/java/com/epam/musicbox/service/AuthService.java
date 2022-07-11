package com.epam.musicbox.service;

import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * Gets token lifetime in seconds.
     *
     * @return the token lifetime
     */
    int getTokenLifetime();

    /**
     * Generate token string.
     *
     * @param claims the claims
     * @return the string
     */
    String generateToken(Map<String, String> claims);


    /**
     * Has token boolean.
     *
     * @param req the http request
     * @return the boolean
     */
    boolean hasToken(HttpServletRequest req);

    /**
     * Gets token.
     *
     * @param req the http request
     * @return the token
     * @throws ServiceException the service exception
     */
    Jws<Claims> getToken(HttpServletRequest req) throws ServiceException;

    /**
     * Sign up.
     *
     * @param login    the login
     * @param email    the email
     * @param password the password
     * @throws ServiceException the service exception
     */
    void signUp(String login, String email, String password) throws ServiceException;

    /**
     * Login user.
     *
     * @param login    the login
     * @param password the password
     * @return the user
     * @throws ServiceException the service exception
     */
    User login(String login, String password) throws ServiceException;

    /**
     * Change password.
     *
     * @param userId      the user id
     * @param oldPassword the old password
     * @param newPassword the new password
     * @throws ServiceException the service exception
     */
    void changePassword(long userId, String oldPassword, String newPassword) throws ServiceException;
}
