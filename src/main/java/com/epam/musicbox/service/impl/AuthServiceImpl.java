package com.epam.musicbox.service.impl;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.hasher.PasswordHasher;
import com.epam.musicbox.hasher.impl.PBKDF2PasswordHasher;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.validator.EntityValidator;
import com.epam.musicbox.validator.impl.EntityValidatorImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LogManager.getLogger();

    private static final String PROP_PATH = "prop/application";
    private static final String JWT_SECRET_KEY = "JWT_SECRET_KEY";
    private static final String JWT_TOKEN_LIFE_TIME = "JWT_LIFETIME";

    private static final String INVALID_LOGIN_MSG = "Invalid login";
    private static final String INVALID_EMAIL_MSG = "Invalid email";
    private static final String INVALID_PASSWORD_MSG = "Invalid password";
    private static final String INVALID_OLD_PASSWORD_MSG = "Invalid old password";
    private static final String INVALID_NEW_PASSWORD_MSG = "Invalid new password";
    private static final String USER_NOT_FOUND = "User not found";
    private static final String USER_WITH_LOGIN_ALREADY_EXIST = "User with this login already exists";
    private static final String USER_WITH_EMAIL_ALREADY_EXIST = "User with this email already exists";
    private static final String USER_BANNED = "User banned";
    private static final String JWT_TOKEN_NOT_FOUND = "Jwt not found";
    private static final String CREATION_ERROR = "Creation error";

    private static final AuthServiceImpl instance = new AuthServiceImpl();

    private final Key secretKey;
    private final long tokenLifetime;
    private final int cookieMaxAge;

    private final UserService userService = UserServiceImpl.getInstance();
    private final PasswordHasher passwordHasher = PBKDF2PasswordHasher.getInstance();
    private final EntityValidator validator = EntityValidatorImpl.getInstance();

    private AuthServiceImpl() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(PROP_PATH);
            this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(resourceBundle.getString(JWT_SECRET_KEY)));
            this.tokenLifetime = Long.parseLong(resourceBundle.getString(JWT_TOKEN_LIFE_TIME));
            int timezoneGmtPlusThree = 60 * 60 * 3;
            this.cookieMaxAge = (int) (timezoneGmtPlusThree + TimeUnit.MINUTES.toSeconds(tokenLifetime));
        } catch (Exception e) {
            logger.fatal(CREATION_ERROR, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static AuthServiceImpl getInstance() {
        return instance;
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public String generateToken(Map<String, String> claims) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Instant instant = Instant.now();
        JwtBuilder builder = Jwts.builder();
        if (claims != null) {
            builder = builder.setClaims(claims);
        }
        return builder.setId(id)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(tokenLifetime, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> buildToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(10)
                .build()
                .parseClaimsJws(token);
    }

    public Optional<Cookie> getTokenCookie(Cookie[] cookies) {
        return cookies == null ?
                Optional.empty() :
                Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(Parameter.ACCESS_TOKEN))
                        .findFirst();
    }

    public Jws<Claims> getToken(HttpServletRequest req) throws ServiceException {
        if (req == null)
            return null;
        Cookie cookie = getTokenCookie(req.getCookies())
                .orElseThrow(() -> new ServiceException(JWT_TOKEN_NOT_FOUND));
        String token = cookie.getValue();
        return buildToken(token);
    }

    public void signUp(String login, String email, String password) throws ServiceException {
        if (!validator.isValidLogin(login))
            throw new ServiceException(INVALID_LOGIN_MSG);
        if (!validator.isValidEmail(email))
            throw new ServiceException(INVALID_EMAIL_MSG);
        if (!validator.isValidPassword(password))
            throw new ServiceException(INVALID_PASSWORD_MSG);

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent())
            throw new ServiceException(USER_WITH_LOGIN_ALREADY_EXIST);

        optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent())
            throw new ServiceException(USER_WITH_EMAIL_ALREADY_EXIST);

        String hash = passwordHasher.hash(password);
        User user = new User(null, login, email, hash, Role.USER, false, Timestamp.from(Instant.now()));
        userService.save(user);
    }

    public User login(String login, String password) throws ServiceException {
        if (!validator.isValidLogin(login))
            throw new ServiceException(INVALID_LOGIN_MSG);
        if (!validator.isValidPassword(password))
            throw new ServiceException(INVALID_PASSWORD_MSG);

        User user = userService.findByLogin(login)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        if (user.getBanned())
            throw new ServiceException(USER_BANNED);

        if (!passwordHasher.checkPassword(password, user.getPassword()))
            throw new ServiceException(INVALID_PASSWORD_MSG);
        return user;
    }

    public void changePassword(long userId, String oldPassword, String newPassword) throws ServiceException {
        if (!validator.isValidPassword(oldPassword))
            throw new ServiceException(INVALID_OLD_PASSWORD_MSG);
        if (!validator.isValidPassword(newPassword))
            throw new ServiceException(INVALID_NEW_PASSWORD_MSG);

        User user = userService.findById(userId)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        if (!passwordHasher.checkPassword(oldPassword, user.getPassword()))
            throw new ServiceException(INVALID_OLD_PASSWORD_MSG);

        String hash = passwordHasher.hash(newPassword);
        user.setPassword(hash);
        userService.save(user);
    }
}
