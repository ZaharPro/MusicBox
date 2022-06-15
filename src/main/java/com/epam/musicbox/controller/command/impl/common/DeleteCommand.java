package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteCommand<T extends Entity> implements Command {

    private final EntityService<T> service;
    private final String id;

    public DeleteCommand(EntityService<T> service, String id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            long id = ParamTaker.getLong(req, this.id);
            service.deleteById(id);
            return CommandResult.forward(PagePath.HOME);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
