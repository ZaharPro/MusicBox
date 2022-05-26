package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserGetLikedTracksCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {

    }
}
