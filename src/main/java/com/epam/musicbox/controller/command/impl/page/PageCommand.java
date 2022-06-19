package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public abstract class PageCommand implements Command {

    private final String pagePath;

    protected PageCommand(String pagePath) {
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        return CommandResult.forward(pagePath);
    }
}
