package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.util.Pages;
import com.epam.musicbox.util.Parameters;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ArtistGetTracksCommand implements Command {
    @Inject
    protected ArtistService artistService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        long artistId = Parameters.getLong(req, Parameter.ARTIST_ID);
        int page = Parameters.getInt(req, Parameter.PAGE);
        List<Track> list = artistService.getTracks(artistId, page);
        req.setAttribute(Parameter.LIST, list);
        Pages.forward(req, resp, Parameter.OBJECT);
    }
}
