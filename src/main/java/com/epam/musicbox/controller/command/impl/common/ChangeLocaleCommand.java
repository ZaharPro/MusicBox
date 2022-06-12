package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String locale = req.getParameter(Parameter.LOCALE);
        HttpSession session = req.getSession();
        session.setAttribute(Parameter.LOCALE, locale);
        return CommandResult.forward(PagePath.HOME);
    }
}