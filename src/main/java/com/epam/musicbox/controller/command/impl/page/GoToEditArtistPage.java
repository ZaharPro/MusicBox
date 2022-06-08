package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToEditArtistPage extends GoToPageCommand {

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    public GoToEditArtistPage() {
        super(PagePath.EDIT_ARTIST);
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long artistId = Parameters.getNullableLong(req, Parameter.ARTIST_ID);
        if (artistId != null) {
            Optional<Artist> optional = artistService.findById(artistId);
            Artist artist = optional.orElse(null);
            req.setAttribute(Parameter.ARTIST, artist);
        }

        Long trackId = Parameters.getNullableLong(req, Parameter.TRACK_ID);
        if (trackId != null) {
            Optional<Track> optionalTrack = trackService.findById(trackId);
            Track track = optionalTrack.orElse(null);
            req.setAttribute(Parameter.TRACK, track);
        }

        Services.handlePage(req, trackService, Parameter.TRACK_PAGE, Parameter.TRACK_LIST);
        return super.execute(req, resp);
    }
}
