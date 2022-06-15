package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PageSearchResult;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class GoToEditPlaylistPageCommand extends GoToPageCommand {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    public GoToEditPlaylistPageCommand() {
        super(PagePath.EDIT_PLAYLIST);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Long playlistId = ParamTaker.getNullableLong(req, Parameter.PLAYLIST_ID);
            if (playlistId != null) {
                Optional<Playlist> optional = playlistService.findById(playlistId);
                Playlist playlist = optional.orElse(null);
                req.setAttribute(Parameter.PLAYLIST, playlist);
            }

            Long trackId = ParamTaker.getNullableLong(req, Parameter.TRACK_ID);
            if (trackId != null) {
                Optional<Track> optionalTrack = trackService.findById(trackId);
                Track track = optionalTrack.orElse(null);
                req.setAttribute(Parameter.TRACK, track);
            }

            int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int pageSize = ParamTaker.getPage(req, Parameter.TRACK_PAGE_SIZE);
            PageSearchResult<Track> pageSearchResult = trackService.findPage(page, pageSize);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, pageSearchResult);

            return super.execute(req);
        }  catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}