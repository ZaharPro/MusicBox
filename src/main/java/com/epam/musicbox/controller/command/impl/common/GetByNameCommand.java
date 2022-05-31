package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Pages;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public abstract class GetByNameCommand<T> implements Command {
    @Inject
    protected Service<T> service;
    protected final String name;
    protected final String attributeName;
    protected final String pagePath;

    public GetByNameCommand(String id, String attributeName, String pagePath) {
        this.name = id;
        this.attributeName = attributeName;
        this.pagePath = pagePath;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String name = req.getParameter(this.name);
        List<T> list = findByName(name);
        req.setAttribute(attributeName, list);
        Pages.forward(req, resp, pagePath);
    }

    protected abstract List<T> findByName(String name);
}
