package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.util.Pages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        HttpSession session = req.getSession();
        session.invalidate();

        Pages.forward(req, resp, PagePath.LOGIN);
    }
}
