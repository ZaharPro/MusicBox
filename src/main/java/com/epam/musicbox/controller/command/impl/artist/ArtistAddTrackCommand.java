package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistAddTrackCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?command=%s&%s=%%s&%s=%%s&%s=%%s&%s=%%s&%s=%%s",
                    CommandType.EDIT_ARTIST_PAGE.getName(),
                    Parameter.ARTIST_ID,
                    Parameter.TRACK_PAGE_INDEX,
                    Parameter.TRACK_PAGE_SIZE,
                    Parameter.ALBUM_PAGE_INDEX,
                    Parameter.ALBUM_PAGE_SIZE);

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            long artistId = ParamTaker.getLong(req, Parameter.ARTIST_ID);
            long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);

            artistService.addTrack(artistId, trackId);

            int trackPage = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int trackPageSize = ParamTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);

            int albumPage = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int albumPageSize = ParamTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);

            String url = String.format(REDIRECT_URL_FORMAT,
                    artistId,
                    trackPage,
                    trackPageSize,
                    albumPage,
                    albumPageSize);
            return CommandResult.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
