package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.guard.AuthGuard;
import com.epam.musicbox.guard.Guard;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddPlaylistCommand implements Command {
    @Inject
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Guard guard = new AuthGuard(req);
        guard.protect();
        HttpSession session = req.getSession();
        Integer userId = ((Integer) session.getAttribute(Parameter.USER_ID));
        String playlistIdSting = req.getParameter(Parameter.PLAYLIST_ID);
        Integer playlistId = ObjectUtils.parseInt(playlistIdSting);
        userService.addPlaylist(userId, playlistId);
    }
}
