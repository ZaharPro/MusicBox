package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import com.epam.musicbox.service.page.TrackArtistPageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditArtistPageCommand extends PageCommand {

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    public EditArtistPageCommand() {
        super(PagePath.EDIT_ARTIST);
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<Long> optionalArtistId = ParameterTaker.getOptionalLong(req, Parameter.ARTIST_ID);
            if (optionalArtistId.isPresent()) {
                long id = optionalArtistId.get();
                Artist artist = artistService.findById(id).orElse(null);
                req.setAttribute(Parameter.ARTIST, artist);

                int page = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
                int pageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
                PageSearchResult<Track> psr = trackService.findPage(page, pageSize);
                psr = TrackArtistPageSearchResult.from(psr, artistService, id);
                req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, psr);
            }
            Router router = super.execute(req);
            router.setCache(false);
            return router;
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}