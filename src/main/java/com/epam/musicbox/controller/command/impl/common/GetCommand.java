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

import java.util.List;

public class GetCommand<T> implements Command {
    @Inject
    protected Service<T> service;
    protected final String attributeName;
    protected final String pagePath;

    public GetCommand(String attributeName, String pagePath) {
        this.attributeName = attributeName;
        this.pagePath = pagePath;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        int page = Parameters.getInt(req, Parameter.PAGE);
        List<T> list = service.findPage(page);
        req.setAttribute(attributeName, list);
        Pages.forward(req, resp, pagePath);
    }
}
