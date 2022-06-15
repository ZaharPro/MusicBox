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
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class GoToEditAlbumPageCommand extends GoToPageCommand {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    public GoToEditAlbumPageCommand() {
        super(PagePath.EDIT_ALBUM);
    }

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
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

        int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE);
        int pageSize = ParamTaker.getPage(req, Parameter.TRACK_PAGE_SIZE);
        List<Track> tracks = trackService.findPage(page, pageSize);
        req.setAttribute(Parameter.TRACK_PAGE, page);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        return super.execute(req);
    }
}
