package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.guard.Guard;
import com.epam.musicbox.guard.ValidationGuard;
import com.epam.musicbox.hasher.PasswordHasher;
import com.epam.musicbox.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class SingUpCommand implements Command {
    @Inject
    private UserService userService;

    @Inject
    private PasswordHasher passwordHasher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String login = req.getParameter(Parameter.LOGIN);
        String email = req.getParameter(Parameter.EMAIL);
        String password = req.getParameter(Parameter.PASSWORD);

        Guard guard = new ValidationGuard(login, email, password);
        guard.protect();

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent())
            throw new HttpException("User with this login already exists", HttpServletResponse.SC_BAD_REQUEST);

        optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent())
            throw new HttpException("User with this email already exists", HttpServletResponse.SC_BAD_REQUEST);

        String hash = passwordHasher.hash(password);
        User user = new User(null, login, email, hash, Timestamp.from(Instant.now()));
        userService.save(user);

        Optional<User> savedUser = userService.findByLogin(login);
        if (savedUser.isEmpty())
            throw new HttpException("Server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        user = savedUser.get();

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.USER_ID, user.getId());
        session.setAttribute(Parameter.LOGIN, user.getLogin());
        session.setAttribute(Parameter.ROLE, Role.USER);
    }
}
