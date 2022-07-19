package com.epam.musicbox.controller;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandProvider;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * The type Controller. Command executor.
 */
@WebServlet(name = "controller", urlPatterns = "/controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 11)
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

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
            Router router = command.execute(req);
            if (!router.isCache()) {
                resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                resp.setHeader("Pragma", "no-cache");
            }
            List<Cookie> cookies = router.getCookies();
            if (!cookies.isEmpty()) {
                cookies.forEach(resp::addCookie);
            }
            String page = router.getPage();
            if (router.isRedirect()) {
                resp.sendRedirect(page);
            } else {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            }
        } catch (CommandException e) {
            logger.error(e);
            req.setAttribute(Parameter.MESSAGE, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}