package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public abstract class GetByNameCommand<T> implements Command {

    private final String name;
    private final String page;
    private final String list;
    private final String pagePath;

    protected GetByNameCommand(String name, String page, String list, String pagePath) {
        this.name = name;
        this.page = page;
        this.list = list;
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String name = req.getParameter(this.name);
        int page = Parameters.getIntOrZero(req, this.page);
        List<T> list = findByName(name, page);
        req.setAttribute(this.page, page);
        req.setAttribute(this.list, list);
        return CommandResult.forward(this.pagePath);
    }

    protected abstract List<T> findByName(String name, int page) throws ServiceException;
}
