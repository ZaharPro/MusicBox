package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Pages;
import com.epam.musicbox.util.Parameters;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GetByIdCommand<T> implements Command {
    @Inject
    protected Service<T> service;
    protected final String idName;
    protected final String pagePath;

    public GetByIdCommand(String id, String pagePath) {
        this.idName = id;
        this.pagePath = pagePath;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        long id = Parameters.getLong(req, idName);
        Optional<T> optional = service.findById(id);
        if (optional.isEmpty()) {
            throw new HttpException("Resource not found", HttpServletResponse.SC_NOT_FOUND);
        }
        T t = optional.get();
        req.setAttribute(Parameter.OBJECT, t);
        Pages.forward(req, resp, pagePath);
    }
}
