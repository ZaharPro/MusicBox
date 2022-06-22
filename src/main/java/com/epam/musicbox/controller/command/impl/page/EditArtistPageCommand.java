package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
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
import com.epam.musicbox.util.ParamTaker;
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
            Long artistId = ParamTaker.getNullableLong(req, Parameter.ARTIST_ID);
            if (artistId != null) {
                Optional<Artist> optional = artistService.findById(artistId);
                Artist artist = optional.orElse(null);
                req.setAttribute(Parameter.ARTIST, artist);

                int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
                int pageSize = ParamTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
                PageSearchResult<Track> pageSearchResult = trackService.findPage(page, pageSize);
                pageSearchResult = TrackArtistPageSearchResult.from(pageSearchResult, artistService, artistId);
                req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, pageSearchResult);
            }

            Long trackId = ParamTaker.getNullableLong(req, Parameter.TRACK_ID);
            if (trackId != null) {
                Optional<Track> optionalTrack = trackService.findById(trackId);
                Track track = optionalTrack.orElse(null);
                req.setAttribute(Parameter.TRACK, track);
            }

            return super.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}