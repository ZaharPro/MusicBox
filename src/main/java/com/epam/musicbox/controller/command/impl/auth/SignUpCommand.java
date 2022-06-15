package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class SignUpCommand implements Command {

    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        String login = req.getParameter(Parameter.LOGIN);
        String email = req.getParameter(Parameter.EMAIL);
        String password = req.getParameter(Parameter.PASSWORD);

        try {
            authService.signUp(login, email, password);
        } catch (ServiceException e) {
            req.setAttribute(Parameter.MESSAGE, e.getMessage());
            return CommandResult.forward(PagePath.SIGN_UP);
        }
        return CommandResult.redirect(PagePath.LOGIN);
    }
}
