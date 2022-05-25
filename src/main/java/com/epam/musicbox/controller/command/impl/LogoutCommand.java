package com.epam.musicbox.controller.command.impl;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute(Parameter.USER_ID);
        session.removeAttribute(Parameter.LOGIN);
    }
}
