package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteCommand<T extends Entity<K>, K> implements Command {

    private final EntityService<T, K> service;
    private final String id;

    public DeleteCommand(EntityService<T, K> service, String id) {
        this.service = service;
        this.id = id;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long id = Parameters.getLong(req, this.id);
        service.deleteById(id);
        return CommandResult.forward(PagePath.HOME);
    }
}
