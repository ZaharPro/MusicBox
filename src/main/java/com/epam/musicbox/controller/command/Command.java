package com.epam.musicbox.controller.command;

import com.epam.musicbox.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    CommandResult execute(HttpServletRequest req) throws CommandException;
}
