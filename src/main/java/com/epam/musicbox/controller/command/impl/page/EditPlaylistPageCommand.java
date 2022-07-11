package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import com.epam.musicbox.service.page.TrackPlaylistPageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditPlaylistPageCommand extends PageCommand {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    public EditPlaylistPageCommand() {
        super(PagePath.EDIT_PLAYLIST);
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<Long> optionalPlaylistId = ParameterTaker.getOptionalLong(req, Parameter.PLAYLIST_ID);
            if (optionalPlaylistId.isPresent()) {
                long id = optionalPlaylistId.get();
                Playlist playlist = playlistService.findById(id).orElse(null);
                req.setAttribute(Parameter.PLAYLIST, playlist);
                int page = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
                int pageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
                PageSearchResult<Track> psr = trackService.findPage(page, pageSize);
                psr = TrackPlaylistPageSearchResult.from(psr,
                        playlistService,
                        id);
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
