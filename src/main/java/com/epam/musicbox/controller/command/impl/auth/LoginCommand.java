package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final AuthService authService = AuthService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);

        User user = authService.login(login, password);
        Optional<Role> optionalRole = userService.getRole(user.getId());
        if (optionalRole.isEmpty())
            throw new ServiceException("User has no role");

        Map<String, String> claims = new HashMap<>();
        claims.put(Parameter.USER_ID, String.valueOf(user.getId()));
        claims.put(Parameter.LOGIN, String.valueOf(user.getLogin()));
        claims.put(Parameter.ROLE, optionalRole.get().getName());

        String token = authService.generateToken(claims);
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(authService.getCookieMaxAge());
        resp.addCookie(cookie);

        return CommandResult.forward(PagePath.HOME);
    }
}
