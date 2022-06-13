package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Commands;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToEditTrackPageCommand extends GoToPageCommand {

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    public GoToEditTrackPageCommand() {
        super(PagePath.EDIT_TRACK);
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long trackId = Parameters.getNullableLong(req, Parameter.TRACK_ID);
        if (trackId != null) {
            Optional<Track> optionalTrack = trackService.findById(trackId);
            Track track = optionalTrack.orElse(null);
            req.setAttribute(Parameter.TRACK, track);
        }

        Long albumId = Parameters.getNullableLong(req, Parameter.ALBUM_ID);
        if (albumId != null) {
            Optional<Album> optional = albumService.findById(albumId);
            Album album = optional.orElse(null);
            req.setAttribute(Parameter.ALBUM, album);
        }

        Commands.handlePage(req, albumService, Parameter.ALBUM_PAGE, Parameter.ALBUM_LIST);
        return super.execute(req, resp);
    }
}
