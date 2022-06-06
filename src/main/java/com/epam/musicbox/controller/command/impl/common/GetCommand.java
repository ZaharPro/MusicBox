package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.Service;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class GetCommand<T> implements Command {

    private final Service<T> service;
    private final String listAttrName;
    private final String pagePath;

    public GetCommand(Service<T> service, String listAttrName, String pagePath) {
        this.service = service;
        this.listAttrName = listAttrName;
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int page = Parameters.getIntOrZero(req, Parameter.PAGE);
        List<T> list = service.findPage(page);
        req.setAttribute(Parameter.PAGE, page);
        req.setAttribute(listAttrName, list);
        return CommandResult.forward(pagePath);
    }
}
