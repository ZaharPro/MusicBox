package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistRemoveTrackCommand implements Command {

    private final ArtistService service = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            long userId = ParamTaker.getLong(req, Parameter.USER_ID);
            long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);
            service.removeTrack(userId, trackId);
            return CommandResult.forward(PagePath.EDIT_ARTIST);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
