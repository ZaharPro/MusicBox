package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class ChangePasswordCommand implements Command {

    private static final String REDIRECT_HOME_PAGE_URL =
            String.format("controller?%s=%s",
                    Parameter.COMMAND,
                    CommandType.HOME_PAGE.getName());

    private static final String REDIRECT_CHANGE_PASSWORD_PAGE_URL =
            String.format("controller?%s=%s&%s=",
                    Parameter.COMMAND,
                    CommandType.CHANGE_PASSWORD_PAGE.getName(),
                    Parameter.MESSAGE);

    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        String oldPassword = req.getParameter(Parameter.OLD_PASSWORD);
        String newPassword = req.getParameter(Parameter.NEW_PASSWORD);

        try {
            Jws<Claims> token = authService.getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            authService.changePassword(userId, oldPassword, newPassword);
            return Router.redirect(REDIRECT_HOME_PAGE_URL);
        } catch (ServiceException e) {
            return Router.redirect(REDIRECT_CHANGE_PASSWORD_PAGE_URL + e.getMessage());
        }
    }
}
