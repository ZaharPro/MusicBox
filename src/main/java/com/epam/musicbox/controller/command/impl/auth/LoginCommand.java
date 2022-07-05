package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements Command {

    private static final String REDIRECT_URL =
            String.format("controller?%s=%s",
                    Parameter.COMMAND,
                    CommandType.HOME_PAGE.getName());

    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);

        User user;
        try {
            user = authService.login(login, password);
        } catch (ServiceException e) {
            req.setAttribute(Parameter.MESSAGE, e.getMessage());
            return Router.forward(PagePath.LOGIN);
        }
        Role role = user.getRole();
        String roleName = role.getName();
        Map<String, String> claims = new HashMap<>();
        claims.put(Parameter.USER_ID, String.valueOf(user.getId()));
        claims.put(Parameter.LOGIN, String.valueOf(user.getLogin()));
        claims.put(Parameter.ROLE, roleName);

        String token = authService.generateToken(claims);
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(authService.getCookieMaxAge());

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.ROLE, roleName);

        Router router = Router.redirect(REDIRECT_URL);
        router.getCookies().add(cookie);
        return router;
    }
}
