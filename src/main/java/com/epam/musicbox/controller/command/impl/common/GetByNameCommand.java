package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public abstract class GetByNameCommand<T> implements Command {

    protected final String listAttrName;
    protected final String pagePath;

    protected GetByNameCommand(String listAttrName, String pagePath) {
        this.listAttrName = listAttrName;
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);
        int page = Parameters.getIntOrZero(req, Parameter.PAGE);
        List<T> list = findByName(name, page);
        req.setAttribute(Parameter.PAGE, page);
        req.setAttribute(listAttrName, list);
        return CommandResult.forward(pagePath);
    }

    protected abstract List<T> findByName(String name, int page) throws ServiceException;
}
