package com.epam.musicbox.controller;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.*;
import com.epam.musicbox.database.ConnectionPool;
import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
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

    private final CommandProvider commandProvider = CommandProvider.getInstance();

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.destroyPool();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String commandName = req.getParameter(Parameter.COMMAND);
            if (commandName == null)
                return;
            CommandType commandType = CommandType.of(commandName);
            if (commandType == null) {
                req.setAttribute(Parameter.ERROR_MESSAGE, "Command doesn't exist");
                RequestDispatcher dispatcher = req.getRequestDispatcher(PagePath.ERROR);
                dispatcher.forward(req, resp);
                return;
            }
            Command command = commandProvider.get(commandType);
            CommandResult commandResult = command.execute(req, resp);
            String page = commandResult.getPage();
            if (commandResult.isRedirect()) {
                resp.sendRedirect(page);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            req.setAttribute(Parameter.ERROR_MESSAGE, e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher(PagePath.ERROR);
            dispatcher.forward(req, resp);
        }
    }
}