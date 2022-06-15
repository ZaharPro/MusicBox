package com.epam.musicbox.controller;

import com.epam.musicbox.controller.command.*;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    public static final Logger logger = LogManager.getLogger();

    private final CommandProvider commandProvider = CommandProvider.getInstance();

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
            CommandType commandType = CommandType.findByName(commandName);
            Command command = commandProvider.get(commandType);
            CommandResult commandResult = command.execute(req);
            String page = commandResult.getPage();
            if (commandResult.isRedirect()) {
                resp.sendRedirect(page);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            }
            List<Cookie> cookies = commandResult.getCookies();
            if (!cookies.isEmpty()) {
                cookies.forEach(resp::addCookie);
            }
        } catch (CommandException e) {
            logger.error(e.getMessage(), e);
            req.setAttribute(Parameter.ERROR_MESSAGE, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}