package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements Command {

    private static final String REDIRECT_HOME_PAGE_URL =
            String.format("controller?%s=%s",
                    Parameter.COMMAND,
                    CommandType.HOME_PAGE.getName());

    private static final String REDIRECT_LOGIN_PAGE_URL =
            String.format("controller?%s=%s&%s=",
                    Parameter.COMMAND,
                    CommandType.LOGIN_PAGE.getName(),
                    Parameter.MESSAGE);

    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);

        User user;
        try {
            user = authService.login(login, password);
        } catch (ServiceException e) {
            return Router.redirect(REDIRECT_LOGIN_PAGE_URL + e.getMessage());
        }
        Map<String, String> claims = new HashMap<>();
        claims.put(Parameter.USER_ID, String.valueOf(user.getId()));
        claims.put(Parameter.LOGIN, user.getLogin());
        claims.put(Parameter.ROLE, user.getRole().getName());
        String token = authService.generateToken(claims);

        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(authService.getTokenLifetime());
        req.getSession().invalidate();

        Router router = Router.redirect(REDIRECT_HOME_PAGE_URL);
        router.getCookies().add(cookie);
        return router;
    }
}
