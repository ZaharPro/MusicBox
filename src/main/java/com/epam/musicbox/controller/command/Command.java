package com.epam.musicbox.controller.command;

import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 */
public interface Command {

    /**
     * Execute command.
     *
     * @param req the http request
     * @return the router
     * @throws CommandException if request parameter is invalid or runtime error
     */
    Router execute(HttpServletRequest req) throws CommandException;
}
