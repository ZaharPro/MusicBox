package com.epam.musicbox.util;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.validator.Validator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class AuthUtils {
    private static final int TIMEZONE_GMT_PLUS_THREE = 60 * 60 * 3;
    public static final int MAX_AGE;

    private static final Key SECRET_KEY;
    private static final long TOKEN_LIFETIME;

    private AuthUtils() {
    }

    static {
        ClassLoader classLoader = AuthUtils.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("prop/application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            String secretKey = (String) properties.get("jwt.secretKey");
            String lifetime = (String) properties.get("jwt.accessToken.lifeTime");
            TOKEN_LIFETIME = Long.parseLong(lifetime);
            SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            MAX_AGE = (int) (TIMEZONE_GMT_PLUS_THREE + TimeUnit.MINUTES.toSeconds(TOKEN_LIFETIME));
        } catch (IOException e) {
            throw new RuntimeException("Error read application properties!", e);
        }
    }

    public static String generateToken(Map<String, String> claims) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(TOKEN_LIFETIME, ChronoUnit.MINUTES)))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(10)
                .build()
                .parseClaimsJws(token);
    }

    public static Optional<Cookie> getTokenFromCookies(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(Parameter.ACCESS_TOKEN))
                .findFirst();
    }

    public static Jws<Claims> getClaimsJws(HttpServletRequest req) throws HttpException {
        Optional<Cookie> optionalCookie = AuthUtils.getTokenFromCookies(req.getCookies());
        if (optionalCookie.isEmpty()) {
            throw new HttpException("JWT token not found", HttpServletResponse.SC_FORBIDDEN);
        }
        String token = optionalCookie.get().getValue();
        return AuthUtils.getClaimsFromToken(token);
    }

    public static void requireValid(Validator validator,
                                    String login,
                                    String email,
                                    String password) throws HttpException {
        StringBuilder sb = new StringBuilder();
        if (!validator.isValidLogin(login)) {
            sb.append("Invalid login");
        }
        if (!validator.isValidEmail(email)) {
            String s = sb.isEmpty() ?
                    "Invalid email" :
                    ", invalid email";
            sb.append(s);
        }
        if (!validator.isValidPassword(password)) {
            String s = sb.isEmpty() ?
                    "Invalid password" :
                    ", invalid password";
            sb.append(s);
        }
        if (!sb.isEmpty()) {
            String msg = sb.toString();
            throw new HttpException(msg, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
