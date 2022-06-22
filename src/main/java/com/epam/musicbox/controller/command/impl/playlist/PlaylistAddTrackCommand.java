package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class PlaylistAddTrackCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?command=%s&%s=%%s&%s=%%s&%s=%%s",
                    CommandType.EDIT_PLAYLIST_PAGE.getName(),
                    Parameter.PLAYLIST_ID,
                    Parameter.TRACK_PAGE_INDEX,
                    Parameter.TRACK_PAGE_SIZE);

    private final PlaylistService service = PlaylistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            long playlistId = ParamTaker.getLong(req, Parameter.PLAYLIST_ID);
            long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);

            service.addTrack(playlistId, trackId);

            int trackPage = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int trackPageSize = ParamTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
            String url = String.format(REDIRECT_URL_FORMAT,
                    playlistId,
                    trackPage,
                    trackPageSize);
            return CommandResult.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
