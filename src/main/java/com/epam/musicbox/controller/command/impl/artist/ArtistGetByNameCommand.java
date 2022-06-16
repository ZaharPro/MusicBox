package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistGetByNameCommand implements Command {

    private static final String COMMAND = "album-get-by-name&name=";

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            String name = req.getParameter(Parameter.NAME);
            int page = ParamTaker.getPage(req, Parameter.ARTIST_PAGE_INDEX);
            int pageSize = ParamTaker.getPageSize(req, Parameter.ARTIST_PAGE_SIZE);
            PageSearchResult<Artist> pageSearchResult = artistService.findByName(name, page, pageSize);
            req.setAttribute(Parameter.ARTIST_PAGE_SEARCH_RESULT, pageSearchResult);
            req.setAttribute(Parameter.COMMAND, COMMAND + name);
            return CommandResult.forward(PagePath.ARTISTS);
        }  catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
