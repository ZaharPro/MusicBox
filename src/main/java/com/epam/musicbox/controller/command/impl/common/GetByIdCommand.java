package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GetByIdCommand<T> implements Command {

    private final Service<T> service;
    private final String idName;
    private final String pagePath;

    public GetByIdCommand(Service<T> service, String idName, String pagePath) {
        this.service = service;
        this.idName = idName;
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        long id = Parameters.getLong(req, idName);
        Optional<T> optional = service.findById(id);
        if (optional.isEmpty()) {
            throw new HttpException("Resource not found", HttpServletResponse.SC_NOT_FOUND);
        }
        T t = optional.get();
        req.setAttribute(Parameter.OBJECT, t);
        return CommandResult.forward(pagePath);
    }
}
