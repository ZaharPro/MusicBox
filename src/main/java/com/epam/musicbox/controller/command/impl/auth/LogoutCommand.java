package com.epam.musicbox.controller.command.impl.auth;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {

    private static final String REDIRECT_URL =
            String.format("controller?%s=%s",
                    Parameter.COMMAND,
                    CommandType.LOGIN_PAGE.getName());

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        Cookie blackToken = new Cookie(Parameter.ACCESS_TOKEN, null);
        blackToken.setMaxAge(0);
        req.getSession().invalidate();
        Router router = Router.redirect(REDIRECT_URL);
        router.getCookies().add(blackToken);
        return router;
    }
}
