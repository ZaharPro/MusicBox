package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.impl.FileServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteCommand<T extends Entity> implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String DELETE_FILE_ERROR_MSG = "Delete file error";

    private final EntityService<T> service;
    private final String id;
    private final String[] entityFields;

    public DeleteCommand(EntityService<T> service, String id, String... entityFields) {
        this.service = service;
        this.id = id;
        this.entityFields = entityFields;
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            long id = ParameterTaker.getLong(req, this.id);
            service.deleteById(id);

            FileService fileService = FileServiceImpl.getInstance();
            for (String entityField : entityFields) {
                String key = FileServiceImpl.generateKey(entityField, id);
                try {
                    fileService.remove(req, key);
                } catch (ServiceException e) {
                    logger.error(DELETE_FILE_ERROR_MSG, e);
                }
            }
            return CommandResult.forward(PagePath.HOME);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
