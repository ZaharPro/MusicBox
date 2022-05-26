package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.hasher.PasswordHasher;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.validator.Validator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginCommand implements Command {
    @Inject
    private UserService userService;

    @Inject
    private PasswordHasher passwordHasher;

    @Inject
    private Validator validator;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String login = req.getParameter(Parameter.LOGIN);
        String email = req.getParameter(Parameter.EMAIL);
        String password = req.getParameter(Parameter.PASSWORD);

        requireValid(login, email, password);

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isEmpty())
            throw new HttpException("User doesn't exists", HttpServletResponse.SC_BAD_REQUEST);

        User user = optionalUser.get();
        if (user.getBanned())
            throw new HttpException("User banned", HttpServletResponse.SC_FORBIDDEN);

        if (!passwordHasher.checkPassword(password, user.getPassword()))
            throw new HttpException("Invalid password", HttpServletResponse.SC_BAD_REQUEST);

        Optional<Role> optionalRole = userService.getRole(user.getId());
        if (optionalRole.isEmpty())
            throw new HttpException("User has no role", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.USER_ID, user.getId());
        session.setAttribute(Parameter.LOGIN, user.getLogin());
        session.setAttribute(Parameter.ROLE, optionalRole.get());
    }

    private void requireValid(String login, String email, String password) throws HttpException {
        String msg = null;
        if (!validator.isValidLogin(login)) {
            msg = "Invalid login";
        }
        if (!validator.isValidEmail(email)) {
            if (msg == null) {
                msg = "Invalid email";
            } else {
                msg = msg + ", invalid email";
            }
        }
        if (!validator.isValidPassword(password)) {
            if (msg == null) {
                msg = "Invalid password";
            } else {
                msg = msg + ", invalid password";
            }
        }
        if (msg != null)
            throw new HttpException(msg, HttpServletResponse.SC_BAD_REQUEST);
    }
}