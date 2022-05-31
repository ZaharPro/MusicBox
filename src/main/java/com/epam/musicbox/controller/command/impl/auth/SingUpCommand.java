package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.hasher.PasswordHasher;
import com.epam.musicbox.hasher.impl.PBKDF2PasswordHasher;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.AuthUtils;
import com.epam.musicbox.validator.Validator;
import com.epam.musicbox.validator.impl.ValidatorImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SingUpCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final PasswordHasher passwordHasher = PBKDF2PasswordHasher.getInstance();

    private final Validator validator = ValidatorImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String login = req.getParameter(Parameter.LOGIN);
        String email = req.getParameter(Parameter.EMAIL);
        String password = req.getParameter(Parameter.PASSWORD);

        AuthUtils.requireValid(validator, login, email, password);

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent())
            throw new HttpException("User with this login already exists", HttpServletResponse.SC_BAD_REQUEST);

        optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent())
            throw new HttpException("User with this email already exists", HttpServletResponse.SC_BAD_REQUEST);

        String hash = passwordHasher.hash(password);
        User user = new User(null, login, email, hash, false, null);
        userService.save(user);

        Optional<User> savedUser = userService.findByLogin(login);
        if (savedUser.isEmpty())
            throw new HttpException("Server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        user = savedUser.get();

        Long userId = user.getId();
        Role role = Role.USER;
        userService.setRole(userId, role.getId());

        Map<String, String> claims = new HashMap<>();
        claims.put(Parameter.USER_ID, String.valueOf(userId));
        claims.put(Parameter.LOGIN, String.valueOf(user.getLogin()));
        claims.put(Parameter.ROLE, role.getName());

        String token = AuthUtils.generateToken(claims);
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(AuthUtils.COOKIE_MAX_AGE);
        resp.addCookie(cookie);
    }
}
