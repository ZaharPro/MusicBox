package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChangePasswordCommand implements Command {

    private final AuthService authService = AuthService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String oldPassword = req.getParameter(Parameter.OLD_PASSWORD);
        String newPassword = req.getParameter(Parameter.NEW_PASSWORD);

        try {
            Jws<Claims> jws = authService.getClaimsJws(req);
            Claims body = jws.getBody();
            long userId = Parameters.getLong(body, Parameter.USER_ID);
            authService.changePassword(userId, oldPassword, newPassword);
        } catch (ServiceException e) {
            req.setAttribute(Parameter.ERROR_MESSAGE, e.getMessage());
            return CommandResult.forward(PagePath.CHANGE_PASSWORD);
        }
        return CommandResult.forward(PagePath.HOME);
    }
}