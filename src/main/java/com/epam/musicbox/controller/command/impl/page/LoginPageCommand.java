package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class LoginPageCommand extends PageCommand {

    public LoginPageCommand() {
        super(PagePath.LOGIN);
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        String message = req.getParameter(Parameter.MESSAGE);
        req.setAttribute(Parameter.MESSAGE, message);
        return super.execute(req);
    }
}
