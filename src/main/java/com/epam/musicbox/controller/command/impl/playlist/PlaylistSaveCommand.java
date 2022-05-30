package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.Parameters;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlaylistSaveCommand implements Command {
    @Inject
    private PlaylistService playlistService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        Long playlistId = Parameters.getNullableLong(req, Parameter.PLAYLIST_ID);
        Long userId = Parameters.getNullableLong(req, Parameter.USER_ID);
        String name = req.getParameter(Parameter.NAME);
        Playlist playlist = new Playlist(playlistId, name, userId);
        playlistService.save(playlist);
    }
}
