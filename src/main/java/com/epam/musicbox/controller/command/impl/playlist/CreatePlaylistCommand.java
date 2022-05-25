package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.guard.AuthGuard;
import com.epam.musicbox.guard.Guard;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CreatePlaylistCommand implements Command {
    @Inject
    private PlaylistService playlistService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Guard guard = new AuthGuard(req, Role.USER, Role.ADMIN);
        guard.protect();
        HttpSession session = req.getSession();
        Integer userId = ((Integer) session.getAttribute(Parameter.USER_ID));
        String name = req.getParameter(Parameter.PLAYLIST_NAME);
        Playlist playlist = new Playlist(null, name, userId);
        playlistService.save(playlist);
    }
}
