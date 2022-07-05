package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class TrackGetByIdCommand implements Command {

    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 20;

    private static final String TRACK_NOT_FOUND_MSG = "Track not found";
    private static final String ALBUM_NOT_FOUND_MSG = "Album not found";

    private final TrackService trackService = TrackServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            long trackId = ParameterTaker.getLong(req, Parameter.TRACK_ID);

            boolean like = userService.isLikedTrack(userId, trackId);
            req.setAttribute(Parameter.LIKE, like);

            Track track = trackService.findById(trackId)
                    .orElseThrow(() -> new CommandException(TRACK_NOT_FOUND_MSG));
            req.setAttribute(Parameter.TRACK, track);

            Album album = albumService.findById(track.getAlbumId())
                    .orElseThrow(() -> new CommandException(ALBUM_NOT_FOUND_MSG));
            req.setAttribute(Parameter.ALBUM, album);

            PageSearchResult<Artist> pageSearchResult = trackService.getArtists(trackId, FIRST_PAGE, PAGE_SIZE);
            req.setAttribute(Parameter.ARTIST_PAGE_SEARCH_RESULT, pageSearchResult);

            return Router.forward(PagePath.TRACK);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
