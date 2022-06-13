package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Commands;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToEditPlaylistPageCommand extends GoToPageCommand {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    public GoToEditPlaylistPageCommand() {
        super(PagePath.EDIT_PLAYLIST);
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long playlistId = Parameters.getNullableLong(req, Parameter.PLAYLIST_ID);
        if (playlistId != null) {
            Optional<Playlist> optional = playlistService.findById(playlistId);
            Playlist playlist = optional.orElse(null);
            req.setAttribute(Parameter.PLAYLIST, playlist);
        }

        Long trackId = Parameters.getNullableLong(req, Parameter.TRACK_ID);
        if (trackId != null) {
            Optional<Track> optionalTrack = trackService.findById(trackId);
            Track track = optionalTrack.orElse(null);
            req.setAttribute(Parameter.TRACK, track);
        }

        Commands.handlePage(req, trackService, Parameter.TRACK_PAGE, Parameter.TRACK_LIST);
        return super.execute(req, resp);
    }
}
