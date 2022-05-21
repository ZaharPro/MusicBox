package com.epam.musicbox.controller.command.impl;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SingUpCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {

    }
}
