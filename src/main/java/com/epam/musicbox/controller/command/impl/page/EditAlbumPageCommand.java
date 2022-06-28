package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class EditAlbumPageCommand extends PageCommand {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    public EditAlbumPageCommand() {
        super(PagePath.EDIT_ALBUM);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<Long> optionalAlbumId = ParameterTaker.getOptionalLong(req, Parameter.ALBUM_ID);
            if (optionalAlbumId.isPresent()) {
                Optional<Album> optional = albumService.findById(optionalAlbumId.get());
                Album album = optional.orElse(null);
                req.setAttribute(Parameter.ALBUM, album);
            }

            Optional<Long> optionalTrackId = ParameterTaker.getOptionalLong(req, Parameter.TRACK_ID);
            if (optionalTrackId.isPresent()) {
                Optional<Track> optionalTrack = trackService.findById(optionalTrackId.get());
                Track track = optionalTrack.orElse(null);
                req.setAttribute(Parameter.TRACK, track);
            }

            return super.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
