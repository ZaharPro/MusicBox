package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteCommand<T> implements Command {

    private final Service<T> service;
    private final String id;

    public DeleteCommand(Service<T> service, String id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long id = Parameters.getLong(req, this.id);
        service.deleteById(id);
        return CommandResult.forward(PagePath.HOME);
    }
}
