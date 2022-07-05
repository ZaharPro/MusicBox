package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        Cookie cookie = new Cookie(Parameter.ACCESS_TOKEN, null);
        cookie.setMaxAge(0);

        HttpSession session = req.getSession();
        session.setAttribute(Parameter.INVALIDATE, true);
        Router router = Router.forward(PagePath.LOGIN);
        router.getCookies().add(cookie);
        return router;
    }
}
