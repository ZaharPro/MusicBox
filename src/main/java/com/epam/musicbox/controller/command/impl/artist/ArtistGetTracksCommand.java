package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ArtistGetTracksCommand implements Command {

    private final ArtistService service = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long artistId = Parameters.getLong(req, Parameter.ARTIST_ID);
        int page = Parameters.getInt(req, Parameter.PAGE);
        List<Track> list = service.getTracks(artistId, page);
        req.setAttribute(Parameter.LIST, list);
        return CommandResult.forward(PagePath.TRACKS);
    }
}
