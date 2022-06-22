package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.psr.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class SearchCommand implements Command {

    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 10;

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            String name = req.getParameter(Parameter.NAME);

            PageSearchResult<Artist> artistPageSearchResult = artistService.findByName(name, FIRST_PAGE, PAGE_SIZE);
            PageSearchResult<Album> albumPageSearchResult = albumService.findByName(name, FIRST_PAGE, PAGE_SIZE);
            PageSearchResult<Track> trackPageSearchResult = trackService.findByName(name, FIRST_PAGE, PAGE_SIZE);

            req.setAttribute(Parameter.ARTIST_PAGE_SEARCH_RESULT, artistPageSearchResult);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, albumPageSearchResult);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, trackPageSearchResult);

            req.setAttribute(Parameter.NAME, name);

            return CommandResult.forward(PagePath.SEARCH);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
