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

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AuthService {
    private static final AuthService instance = new AuthService();

    private static final int TIMEZONE_GMT_PLUS_THREE = 60 * 60 * 3;

    private final Key secretKey;
    private final long tokenLifetime;
    private final int cookieMaxAge;

    private final UserService userService = UserServiceImpl.getInstance();
    private final PasswordHasher passwordHasher = PBKDF2PasswordHasher.getInstance();
    private final Validator validator = ValidatorImpl.getInstance();

    private AuthService() {
        ClassLoader classLoader = AuthService.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("prop/application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            String secretKey = (String) properties.get("jwt.secretKey");
            String lifetime = (String) properties.get("jwt.accessToken.lifeTime");
            tokenLifetime = Long.parseLong(lifetime);
            this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            cookieMaxAge = (int) (TIMEZONE_GMT_PLUS_THREE + TimeUnit.MINUTES.toSeconds(tokenLifetime));
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
            throw new ServiceException("JWT token not found");
        }
        String token = optionalCookie.get().getValue();
        return getClaimsFromToken(token);
    }

    public void signup(String login, String email, String password) throws ServiceException {
        StringBuilder sb = new StringBuilder();
        if (!validator.isValidLogin(login)) {
            sb.append("Invalid login");
        }
        if (!validator.isValidEmail(email)) {
            String s = sb.length() == 0 ?
                    "Invalid email" :
                    ", invalid email";
            sb.append(s);
        }
        if (!validator.isValidPassword(password)) {
            String s = sb.length() == 0 ?
                    "Invalid password" :
                    ", invalid password";
            sb.append(s);
        }
        if (sb.length() != 0) {
            String msg = sb.toString();
            throw new ServiceException(msg);
        }

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
        StringBuilder sb = new StringBuilder();
        if (!validator.isValidLogin(login)) {
            sb.append("Invalid login");
        }
        if (!validator.isValidPassword(password)) {
            String s = sb.length() == 0 ?
                    "Invalid password" :
                    ", invalid password";
            sb.append(s);
        }
        if (sb.length() != 0) {
            String msg = sb.toString();
            throw new ServiceException(msg);
        }

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

    public void changePassword(long userId, String currentPassword, String newPassword) throws ServiceException {
        if (validator.isValidPassword(currentPassword))
            throw new ServiceException("Invalid current password");
        if (validator.isValidPassword(newPassword))
            throw new ServiceException("Invalid new password");

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty())
            throw new ServiceException("User not exist");
        User user = optionalUser.get();

        if (!passwordHasher.checkPassword(currentPassword, user.getPassword()))
            throw new ServiceException("Invalid current password");
        user.setPassword(newPassword);
        userService.save(user);
    }
}
