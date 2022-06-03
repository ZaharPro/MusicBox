package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ArtistRemoveTrackCommand implements Command {

    private final ArtistService service = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        long userId = Parameters.getLong(req, Parameter.USER_ID);
        long trackId = Parameters.getLong(req, Parameter.TRACK_ID);
        service.removeTrack(userId, trackId);
        return CommandResult.refresh();
    }
}
