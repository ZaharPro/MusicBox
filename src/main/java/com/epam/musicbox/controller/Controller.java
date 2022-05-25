package com.epam.musicbox.controller;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandProvider;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.exception.HttpException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    public static final Logger logger = LogManager.getLogger();

    @Inject
    private CommandProvider commandProvider;

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String commandName = req.getParameter(Parameter.COMMAND);
            CommandType commandType = CommandType.of(commandName);
            Command command = commandProvider.get(commandType);
            command.execute(req, resp);
        } catch (HttpException e) {
            logger.error(e.getMessage(), e);
            resp.sendError(e.getStatusCode(), e.getMessage());
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            resp.sendError(HttpException.DEFAULT_STATUS_CODE, e.getMessage());
        }
    }
}