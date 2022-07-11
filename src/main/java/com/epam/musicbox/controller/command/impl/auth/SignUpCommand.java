package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {

    private static final String REDIRECT_LOGIN_PAGE_URL =
            String.format("controller?%s=%s",
                    Parameter.COMMAND,
                    CommandType.LOGIN_PAGE.getName());

    private static final String REDIRECT_SIGN_UP_PAGE_URL =
            String.format("controller?%s=%s&%s=",
                    Parameter.COMMAND,
                    CommandType.SIGN_UP_PAGE.getName(),
                    Parameter.MESSAGE);

    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        String login = req.getParameter(Parameter.LOGIN);
        String email = req.getParameter(Parameter.EMAIL);
        String password = req.getParameter(Parameter.PASSWORD);
        try {
            authService.signUp(login, email, password);
            return Router.redirect(REDIRECT_LOGIN_PAGE_URL);
        } catch (ServiceException e) {
            return Router.redirect(REDIRECT_SIGN_UP_PAGE_URL + e.getMessage());
        }
    }
}
