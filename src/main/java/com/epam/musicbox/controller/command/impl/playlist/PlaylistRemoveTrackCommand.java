package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlaylistRemoveTrackCommand implements Command {
    @Inject
    private PlaylistService playlistService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String playlistIdSting = req.getParameter(Parameter.PLAYLIST_ID);
        Integer playlistId = ObjectUtils.parseInt(playlistIdSting);

        String trackIdString = req.getParameter(Parameter.PLAYLIST_ID);
        Integer trackId = ObjectUtils.parseInt(trackIdString);
        playlistService.removeTrack(playlistId, trackId);
    }
}
