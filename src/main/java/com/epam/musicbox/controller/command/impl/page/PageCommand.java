package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class PageCommand implements Command {

    private final String pagePath;

    public PageCommand(String pagePath) {
        this.pagePath = pagePath;
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        return Router.forward(pagePath);
    }
}
