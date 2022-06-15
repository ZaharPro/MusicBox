package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangeLocaleCommand implements Command {

    public static final String INVALID_LOCALE_MSG = "Invalid locale";
    public static final String LOCALE_EN = "en_EN";
    public static final String LOCALE_RU = "ru_RU";

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        String locale = req.getParameter(Parameter.LOCALE);
        if (!(LOCALE_EN.equals(locale) || LOCALE_RU.equals(locale))) {
            throw new ServiceException(INVALID_LOCALE_MSG);
        }
        HttpSession session = req.getSession();
        session.setAttribute(Parameter.LOCALE, locale);
        return CommandResult.forward(PagePath.HOME);
    }
}