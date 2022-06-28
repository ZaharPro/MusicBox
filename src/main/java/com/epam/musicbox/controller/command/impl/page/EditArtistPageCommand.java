package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.service.psr.TrackArtistPageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditArtistPageCommand extends PageCommand {

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    public EditArtistPageCommand() {
        super(PagePath.EDIT_ARTIST);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<Long> artistId = ParameterTaker.getOptionalLong(req, Parameter.ARTIST_ID);
            if (artistId.isPresent()) {
                Artist artist = artistService.findById(artistId.get()).orElse(null);
                req.setAttribute(Parameter.ARTIST, artist);

                int page = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
                int pageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
                PageSearchResult<Track> pageSearchResult = trackService.findPage(page, pageSize);
                pageSearchResult = TrackArtistPageSearchResult.from(pageSearchResult, artistService, artistId.get());
                req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, pageSearchResult);
            }

            Optional<Long> trackId = ParameterTaker.getOptionalLong(req, Parameter.TRACK_ID);
            if (trackId.isPresent()) {
                Track track = trackService.findById(trackId.get()).orElse(null);
                req.setAttribute(Parameter.TRACK, track);
            }

            return super.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}