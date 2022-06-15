package com.epam.musicbox.service;

import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface AuthService {

    int getCookieMaxAge();

    String generateToken(Map<String, String> claims);

    Jws<Claims> getToken(HttpServletRequest req) throws ServiceException;

    void signUp(String login, String email, String password) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    void changePassword(long userId, String oldPassword, String newPassword) throws ServiceException;
}
