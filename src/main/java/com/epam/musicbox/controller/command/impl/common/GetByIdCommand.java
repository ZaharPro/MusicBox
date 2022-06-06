package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GetByIdCommand<T> implements Command {

    private final Service<T> service;
    private final String idName;
    private final String objectAttrName;
    private final String pagePath;

    public GetByIdCommand(Service<T> service, String idName, String objectAttrName, String pagePath) {
        this.service = service;
        this.idName = idName;
        this.objectAttrName = objectAttrName;
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long id = Parameters.getLong(req, idName);
        Optional<T> optional = service.findById(id);
        if (optional.isEmpty()) {
            throw new ServiceException("Resource not found");
        }
        T t = optional.get();
        req.setAttribute(objectAttrName, t);
        return CommandResult.forward(pagePath);
    }
}
