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
    private static final AuthService instance = createInstance();
    private static final ResourceBundle resourceBundle;

    private static final int TIMEZONE_GMT_PLUS_THREE = 60 * 60 * 3;

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

    static {
        if (System.getenv("Env") == null) {
            resourceBundle = ResourceBundle.getBundle("prop/database");
        } else {
            throw new RuntimeException("Database properties not found");
        }
    }

    private static String getProperty(String propertyName) {
        String valueFromEnv = System.getenv(propertyName);
        if (valueFromEnv != null) {
            return valueFromEnv;
        }

        try {
            return resourceBundle.getString(propertyName);
        } catch (MissingResourceException e) {
            throw new RuntimeException(String.format("Property %s is not found", propertyName));
        }
    }

    private static AuthService createInstance() {
        Key secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(getProperty("jwt.secretKey")));
        long tokenLifetime = Long.parseLong(getProperty("jwt.accessToken.lifeTime"));
        int cookieMaxAge = (int) (TIMEZONE_GMT_PLUS_THREE + TimeUnit.MINUTES.toSeconds(tokenLifetime));
        return new AuthService(secretKey, tokenLifetime, cookieMaxAge);
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
            throw new ServiceException("JWT token not found");
        }
        String token = optionalCookie.get().getValue();
        return getClaimsFromToken(token);
    }

    public void signup(String login, String email, String password) throws ServiceException {
        if (!validator.isValidLogin(login))
            throw new ServiceException("Invalid login");
        if (!validator.isValidEmail(email))
            throw new ServiceException("Invalid email");
        if (!validator.isValidPassword(password))
            throw new ServiceException("Invalid password");

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent())
            throw new ServiceException("User with this login already exists");

        optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent())
            throw new ServiceException("User with this email already exists");

        String hash = passwordHasher.hash(password);
        User user = new User(null, login, email, hash, false, Timestamp.from(Instant.now()));
        long userId = userService.save(user);
        Role role = Role.USER;
        userService.setRole(userId, role.getId());
    }

    public User login(String login, String password) throws ServiceException {
        if (!validator.isValidLogin(login))
            throw new ServiceException("Invalid login");
        if (!validator.isValidPassword(password))
            throw new ServiceException("Invalid password");

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isEmpty())
            throw new ServiceException("User doesn't exists");

        User user = optionalUser.get();
        if (user.getBanned())
            throw new ServiceException("User banned");

        if (!passwordHasher.checkPassword(password, user.getPassword()))
            throw new ServiceException("Invalid password");
        return user;
    }

    public void changePassword(long userId, String oldPassword, String newPassword) throws ServiceException {
        if (!validator.isValidPassword(oldPassword))
            throw new ServiceException("Invalid old password");
        if (!validator.isValidPassword(newPassword))
            throw new ServiceException("Invalid new password");

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty())
            throw new ServiceException("User not exist");
        User user = optionalUser.get();

        if (!passwordHasher.checkPassword(oldPassword, user.getPassword()))
            throw new ServiceException("Invalid current password");
        String hash = passwordHasher.hash(newPassword);
        user.setPassword(hash);
        userService.save(user);
    }
}
