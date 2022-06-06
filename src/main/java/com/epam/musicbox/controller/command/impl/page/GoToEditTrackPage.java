package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.util.Parameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class GoToEditTrackPage extends GoToPageCommand {

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    public GoToEditTrackPage() {
        super(PagePath.EDIT_TRACK);
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long nullableTrackId = Parameters.getNullableLong(req, Parameter.TRACK_ID);
        if (nullableTrackId != null) {
            Optional<Track> optionalTrack = trackService.findById(nullableTrackId);
            if (optionalTrack.isPresent()) {
                Track track = optionalTrack.get();
                req.setAttribute(Parameter.TRACK, track);
            }
        }

        Long nullableAlbumId = Parameters.getNullableLong(req, Parameter.ALBUM_ID);
        if (nullableAlbumId != null) {
            Optional<Album> optionalAlbum = albumService.findById(nullableAlbumId);
            if (optionalAlbum.isPresent()) {
                Album album = optionalAlbum.get();
                req.setAttribute(Parameter.ALBUM, album);
            }
        }

        int page = Parameters.getIntOrZero(req, Parameter.PAGE);
        List<Album> list = albumService.findPage(page);
        req.setAttribute(Parameter.ALBUM_LIST, list);
        return super.execute(req, resp);
    }
}
