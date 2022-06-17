package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class GoToEditAlbumPageCommand extends GoToPageCommand {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    public GoToEditAlbumPageCommand() {
        super(PagePath.EDIT_ALBUM);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Long albumId = ParamTaker.getNullableLong(req, Parameter.ALBUM_ID);
            if (albumId != null) {
                Optional<Album> optional = albumService.findById(albumId);
                Album album = optional.orElse(null);
                req.setAttribute(Parameter.ALBUM, album);
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
