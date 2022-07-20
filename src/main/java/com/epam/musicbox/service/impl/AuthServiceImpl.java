package com.epam.musicbox.service.impl;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.hasher.Hasher;
import com.epam.musicbox.util.hasher.impl.PBKDF2Hasher;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import io.jsonwebtoken.*;
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

public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LogManager.getLogger();

    private static final String PROPS_PATH = "prop/application";
    private static final String JWT_SECRET_KEY = "JWT_SECRET_KEY";
    private static final String JWT_LIFETIME = "JWT_LIFETIME";

    private static final String PROP_NOT_FOUND_MSG = "Auth service property not found: ";
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

    private static final AuthServiceImpl instance = new AuthServiceImpl();
    private static final ResourceBundle bundle = ResourceBundle.getBundle(PROPS_PATH);

    private final UserService userService = UserServiceImpl.getInstance();
    private final Hasher hasher = PBKDF2Hasher.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    private final Key secretKey;
    private final int tokenLifetime;

    private AuthServiceImpl() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(getProp(JWT_SECRET_KEY)));
        this.tokenLifetime = Integer.parseInt(getProp(JWT_LIFETIME));
    }

    private static String getProp(String name) {
        String val = System.getenv(name);
        if (val == null && bundle != null)
            val = bundle.getString(name);
        if (val == null) {
            logger.fatal(PROP_NOT_FOUND_MSG + name);
            throw new ExceptionInInitializerError(PROP_NOT_FOUND_MSG + name);
        }
        return val;
    }

    public static AuthServiceImpl getInstance() {
        return instance;
    }

    public int getTokenLifetime() {
        return tokenLifetime;
    }

    public String generateToken(Map<String, String> claims) {
        String id = UUID.randomUUID().toString().replace("-", "");
        JwtBuilder builder = Jwts.builder();
        if (claims != null) {
            builder = builder.setClaims(claims);
        }
        Instant instant = Instant.now();
        return builder.setId(id)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(tokenLifetime, ChronoUnit.SECONDS)))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> parseToken(String token) throws ServiceException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .setAllowedClockSkewSeconds(10)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Optional<Cookie> getTokenCookie(Cookie[] cookies) {
        return cookies == null ?
                Optional.empty() :
                Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(Parameter.ACCESS_TOKEN))
                        .findAny();
    }

    @Override
    public boolean hasToken(HttpServletRequest req) {
        return req != null && getTokenCookie(req.getCookies()).isPresent();
    }

    @Override
    public Jws<Claims> getToken(HttpServletRequest req) throws ServiceException {
        Cookie cookie = getTokenCookie(req == null ? null : req.getCookies())
                .orElseThrow(() -> new ServiceException(JWT_TOKEN_NOT_FOUND));
        String token = cookie.getValue();
        return parseToken(token);
    }

    @Override
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

        String hash = hasher.hash(password);
        User user = new User(null, login, email, hash, Role.USER, false, Timestamp.from(Instant.now()));
        userService.save(user);
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        if (!validator.isValidLogin(login))
            throw new ServiceException(INVALID_LOGIN_MSG);
        if (!validator.isValidPassword(password))
            throw new ServiceException(INVALID_PASSWORD_MSG);

        User user = userService.findByLogin(login)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        if (user.getBanned())
            throw new ServiceException(USER_BANNED);

        if (!hasher.check(password, user.getPassword()))
            throw new ServiceException(INVALID_PASSWORD_MSG);
        return user;
    }

    @Override
    public void changePassword(long userId, String oldPassword, String newPassword) throws ServiceException {
        if (!validator.isValidPassword(oldPassword))
            throw new ServiceException(INVALID_OLD_PASSWORD_MSG);
        if (!validator.isValidPassword(newPassword))
            throw new ServiceException(INVALID_NEW_PASSWORD_MSG);

        User user = userService.findById(userId)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        if (!hasher.check(oldPassword, user.getPassword()))
            throw new ServiceException(INVALID_OLD_PASSWORD_MSG);

        String hash = hasher.hash(newPassword);
        user.setPassword(hash);
        userService.save(user);
    }
}
