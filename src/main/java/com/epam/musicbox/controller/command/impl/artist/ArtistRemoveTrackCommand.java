package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistRemoveTrackCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?%s=%s&%s=%%s&%s=%%s&%s=%%s&%s=%%s&%s=%%s",
                    Parameter.COMMAND,
                    CommandType.EDIT_ARTIST_PAGE.getName(),
                    Parameter.ARTIST_ID,
                    Parameter.TRACK_PAGE_INDEX,
                    Parameter.TRACK_PAGE_SIZE,
                    Parameter.ALBUM_PAGE_INDEX,
                    Parameter.ALBUM_PAGE_SIZE);

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            long artistId = ParameterTaker.getLong(req, Parameter.ARTIST_ID);
            long trackId = ParameterTaker.getLong(req, Parameter.TRACK_ID);

            artistService.removeTrack(artistId, trackId);

            int trackPage = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int trackPageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);

            int albumPage = ParameterTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int albumPageSize = ParameterTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);

            String url = String.format(REDIRECT_URL_FORMAT,
                    artistId,
                    trackPage,
                    trackPageSize,
                    albumPage,
                    albumPageSize);
            return Router.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
