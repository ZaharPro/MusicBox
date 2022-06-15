package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ArtistGetByNameCommand implements Command {

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        String name = req.getParameter(Parameter.NAME);
        int page = ParamTaker.getPage(req, Parameter.ARTIST_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.ARTIST_PAGE_SIZE);
        List<Artist> artists = artistService.findByName(name, page, pageSize);
        req.setAttribute(Parameter.ARTIST_PAGE, page);
        req.setAttribute(Parameter.ARTIST_LIST, artists);
        return CommandResult.forward(PagePath.ARTISTS_BY_NAME);
    }
}
