package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginCommand implements Command {

    private static final String REDIRECT_URL = "controller?command=" + CommandType.HOME_PAGE.getName();
    private static final String USER_ROLE_NOT_FOUND = "User role not found";

    private final UserService userService = UserServiceImpl.getInstance();
    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);

        User user;
        try {
            user = authService.login(login, password);
        } catch (ServiceException e) {
            req.setAttribute(Parameter.MESSAGE, e.getMessage());
            return CommandResult.forward(PagePath.LOGIN);
        }


        Optional<Role> optionalRole;
        try {
            optionalRole = userService.getRole(user.getId());
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (optionalRole.isEmpty())
            throw new CommandException(USER_ROLE_NOT_FOUND);
        Role role = optionalRole.get();

        Map<String, String> claims = new HashMap<>();
        claims.put(Parameter.USER_ID, String.valueOf(user.getId()));
        claims.put(Parameter.LOGIN, String.valueOf(user.getLogin()));
        claims.put(Parameter.ROLE, role.getValue());

        String token = authService.generateToken(claims);
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(authService.getCookieMaxAge());

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.ROLE, role.getValue());

        CommandResult commandResult = CommandResult.redirect(REDIRECT_URL);
        commandResult.getCookies().add(cookie);
        return commandResult;
    }
}
