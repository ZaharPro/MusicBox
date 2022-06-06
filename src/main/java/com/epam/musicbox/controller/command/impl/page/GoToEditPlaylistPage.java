package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class GoToEditPlaylistPage extends GoToPageCommand {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    public GoToEditPlaylistPage() {
        super(PagePath.EDIT_PLAYLIST);
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long nullablePlaylistId = Parameters.getNullableLong(req, Parameter.PLAYLIST_ID);
        if (nullablePlaylistId != null) {
            Optional<Playlist> optionalPlaylist = playlistService.findById(nullablePlaylistId);
            if (optionalPlaylist.isPresent()) {
                Playlist playlist = optionalPlaylist.get();
                req.setAttribute(Parameter.PLAYLIST, playlist);
            }
        }

        Long nullableTrackId = Parameters.getNullableLong(req, Parameter.ALBUM_ID);
        if (nullableTrackId != null) {
            Optional<Track> optionalTrack = trackService.findById(nullableTrackId);
            if (optionalTrack.isPresent()) {
                Track track = optionalTrack.get();
                req.setAttribute(Parameter.ALBUM, track);
            }
        }

        int page = Parameters.getIntOrZero(req, Parameter.PAGE);
        List<Track> list = trackService.findPage(page);
        req.setAttribute(Parameter.ALBUM_LIST, list);
        return super.execute(req, resp);
    }
}
