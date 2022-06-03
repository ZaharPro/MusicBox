package com.epam.musicbox.controller.command;

import com.epam.musicbox.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
    CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException;
}
