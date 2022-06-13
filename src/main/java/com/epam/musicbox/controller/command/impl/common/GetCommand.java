package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class GetCommand<T extends Entity<K>, K> implements Command {

    private final EntityService<T, K> service;
    private final String page;
    private final String list;
    private final String pagePath;

    public GetCommand(EntityService<T, K> service, String page, String list, String pagePath) {
        this.service = service;
        this.page = page;
        this.list = list;
        this.pagePath = pagePath;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        int page = Parameters.getIntOrZero(req, this.page);
        List<T> list = this.service.findPage(page);
        req.setAttribute(this.page, page);
        req.setAttribute(this.list, list);
        return CommandResult.forward(this.pagePath);
    }
}
