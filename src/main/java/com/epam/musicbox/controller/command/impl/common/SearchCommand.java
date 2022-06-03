package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class SearchCommand implements Command {
    private static final int MAX_LIST_SIZE = 7;

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String name = req.getParameter(Parameter.NAME);

        List<Artist> artistList = cutList(artistService.findByName(name, 0));
        List<Album> albumList = cutList(albumService.findByName(name, 0));
        List<Track> trackList = cutList(trackService.findByName(name, 0));

        req.setAttribute(Parameter.ARTIST_LIST, artistList);
        req.setAttribute(Parameter.ALBUM_LIST, albumList);
        req.setAttribute(Parameter.TRACK_LIST, trackList);

        return CommandResult.forward(PagePath.SEARCH);
    }

    private static <T> List<T> cutList(List<T> list) {
        return list.size() > MAX_LIST_SIZE ?
                list.subList(0, MAX_LIST_SIZE) :
                list;
    }
}
