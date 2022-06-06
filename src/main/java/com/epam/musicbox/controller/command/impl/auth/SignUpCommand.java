package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {

    private final AuthService authService = AuthService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(Parameter.LOGIN);
        String email = req.getParameter(Parameter.EMAIL);
        String password = req.getParameter(Parameter.PASSWORD);

        try {
            authService.signup(login, email, password);
        } catch (ServiceException e) {
            req.setAttribute(Parameter.ERROR_MESSAGE, e.getMessage());
            return CommandResult.forward(PagePath.LOGIN);
        }
        req.setAttribute(Parameter.LOGIN, login);
        return CommandResult.forward(PagePath.LOGIN);
    }
}
