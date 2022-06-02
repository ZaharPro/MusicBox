package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.Pages;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.INVALIDATE, true);

        Pages.forward(req, resp, PagePath.LOGIN);
    }
}
