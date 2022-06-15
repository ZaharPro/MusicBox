package com.epam.musicbox.controller.command.impl.common;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
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
    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 20;

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        String name = req.getParameter(Parameter.NAME);

        List<Artist> artistList = cutList(artistService.findByName(name, FIRST_PAGE, PAGE_SIZE));
        List<Album> albumList = cutList(albumService.findByName(name, FIRST_PAGE, PAGE_SIZE));
        List<Track> trackList = cutList(trackService.findByName(name, FIRST_PAGE, PAGE_SIZE));

        req.setAttribute(Parameter.ARTIST_LIST, artistList);
        req.setAttribute(Parameter.ALBUM_LIST, albumList);
        req.setAttribute(Parameter.TRACK_LIST, trackList);
        req.setAttribute(Parameter.NAME, name);

        return CommandResult.forward(PagePath.SEARCH);
    }

    private static <T> List<T> cutList(List<T> list) {
        return list.size() > MAX_LIST_SIZE ?
                list.subList(0, MAX_LIST_SIZE) :
                list;
    }
}
