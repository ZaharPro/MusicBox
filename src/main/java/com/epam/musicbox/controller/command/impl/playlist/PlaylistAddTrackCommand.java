package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PlaylistAddTrackCommand implements Command {

    private final PlaylistService service = PlaylistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long playlistId = Parameters.getLong(req, Parameter.PLAYLIST_ID);
        long trackId = Parameters.getLong(req, Parameter.TRACK_ID);
        service.addTrack(playlistId, trackId);
        return CommandResult.forward(PagePath.EDIT_PLAYLIST);
    }
}
