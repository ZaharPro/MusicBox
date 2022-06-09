package com.epam.musicbox.service;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.hasher.PasswordHasher;
import com.epam.musicbox.hasher.impl.PBKDF2PasswordHasher;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AuthService {

    private static final String PROP_PATH = "prop/application.properties";
    private static final String JWT_SECRET_KEY = "jwt.secretKey";
    private static final String JWT_TOKEN_LIFE_TIME = "jwt.accessToken.lifeTime";
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

    private static final AuthService instance = createInstance();

    private final Key secretKey;
    private final long tokenLifetime;
    private final int cookieMaxAge;

    private final UserService userService = UserServiceImpl.getInstance();
    private final PasswordHasher passwordHasher = PBKDF2PasswordHasher.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    private AuthService(Key secretKey, long tokenLifetime, int cookieMaxAge) {
        this.secretKey = secretKey;
        this.tokenLifetime = tokenLifetime;
        this.cookieMaxAge = cookieMaxAge;
    }

    private static AuthService createInstance() {
        ClassLoader classLoader = AuthService.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(PROP_PATH)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Key secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode((String) properties.get(JWT_SECRET_KEY)));
            long tokenLifetime = Long.parseLong((String) properties.get(JWT_TOKEN_LIFE_TIME));
            int timezoneGmtPlusThree = 60 * 60 * 3;
            int cookieMaxAge = (int) (timezoneGmtPlusThree + TimeUnit.MINUTES.toSeconds(tokenLifetime));
            return new AuthService(secretKey, tokenLifetime, cookieMaxAge);
        } catch (IOException e) {
            throw new RuntimeException("Error read application properties!", e);
        }
    }

    public static AuthService getInstance() {
        return instance;
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public String generateToken(Map<String, String> claims) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(tokenLifetime, ChronoUnit.MINUTES)))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(10)
                .build()
                .parseClaimsJws(token);
    }

    public Optional<Cookie> getTokenFromCookies(Cookie[] cookies) {
        return cookies == null ?
                Optional.empty() :
                Arrays.stream(cookies)
                        .filter(c -> c.getName().equals(Parameter.ACCESS_TOKEN))
                        .findFirst();
    }

    public Jws<Claims> getClaimsJws(HttpServletRequest req) throws ServiceException {
        Optional<Cookie> optionalCookie = getTokenFromCookies(req.getCookies());
        if (optionalCookie.isEmpty()) {
            throw new ServiceException(JWT_TOKEN_NOT_FOUND);
        }
        String token = optionalCookie.get().getValue();
        return getClaimsFromToken(token);
    }

    public void signup(String login, String email, String password) throws ServiceException {
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
        User user = new User(null, login, email, hash, false, Timestamp.from(Instant.now()));
        long userId = userService.save(user);
        userService.setRole(userId, Role.USER.getId());
    }

    public User login(String login, String password) throws ServiceException {
        if (!validator.isValidLogin(login))
            throw new ServiceException(INVALID_LOGIN_MSG);
        if (!validator.isValidPassword(password))
            throw new ServiceException(INVALID_PASSWORD_MSG);

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isEmpty())
            throw new ServiceException(USER_NOT_FOUND);

        User user = optionalUser.get();
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

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty())
            throw new ServiceException(USER_NOT_FOUND);

        User user = optionalUser.get();
        if (!passwordHasher.checkPassword(oldPassword, user.getPassword()))
            throw new ServiceException(INVALID_OLD_PASSWORD_MSG);

        String hash = passwordHasher.hash(newPassword);
        user.setPassword(hash);
        userService.save(user);
    }
}
