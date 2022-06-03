package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.INVALIDATE, true);
        return CommandResult.forward(PagePath.LOGIN);
    }
}
