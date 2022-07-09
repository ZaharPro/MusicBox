package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.service.FileService;
import com.epam.musicbox.service.impl.FileServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.LongFunction;

public class DeleteCommand<T extends Entity> implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String DELETE_FILE_ERROR_MSG = "Delete file error";

    private final EntityService<T> service;
    private final String id;
    private final LongFunction<String>[] keyGenByIdFunctions;

    @SafeVarargs
    public DeleteCommand(EntityService<T> service, String id, LongFunction<String>... keyGenByIdFunctions) {
        this.service = service;
        this.id = id;
        this.keyGenByIdFunctions = keyGenByIdFunctions;
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            long id = ParameterTaker.getLong(req, this.id);
            service.deleteById(id);

            FileService fileService = FileServiceImpl.getInstance();
            String dir = FileService.getUploadDir(req);
            for (LongFunction<String> function : keyGenByIdFunctions) {
                try {
                    fileService.remove(dir, function.apply(id));
                } catch (ServiceException e) {
                    logger.error(DELETE_FILE_ERROR_MSG, e);
                }
            }
            return Router.forward(PagePath.HOME);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
