package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteCommand<T> implements Command {

    private final Service<T> service;
    private final String idName;

    public DeleteCommand(Service<T> service, String idName) {
        this.service = service;
        this.idName = idName;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        long id = Parameters.getLong(req, idName);
        service.deleteById(id);
        return CommandResult.refresh();
    }
}
