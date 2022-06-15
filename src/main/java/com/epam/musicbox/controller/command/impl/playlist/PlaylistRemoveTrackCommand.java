package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class PlaylistRemoveTrackCommand implements Command {

    private final PlaylistService service = PlaylistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            long playlistId = ParamTaker.getLong(req, Parameter.PLAYLIST_ID);
            long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);
            service.removeTrack(playlistId, trackId);
            return CommandResult.forward(PagePath.EDIT_PLAYLIST);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
