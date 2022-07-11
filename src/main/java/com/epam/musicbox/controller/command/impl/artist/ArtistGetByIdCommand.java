package com.epam.musicbox.controller.command.impl.artist;

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
import com.epam.musicbox.service.ArtistService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistGetByIdCommand implements Command {

    private static final String ARTIST_NOT_FOUND_MSG = "Artist not found";

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            long artistId = ParameterTaker.getLong(req, Parameter.ARTIST_ID);

            boolean like = userService.isLikedArtist(userId, artistId);
            req.setAttribute(Parameter.LIKE, like);

            Artist artist = artistService.findById(artistId)
                    .orElseThrow(() -> new CommandException(ARTIST_NOT_FOUND_MSG));
            req.setAttribute(Parameter.ARTIST, artist);

            int page = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
            PageSearchResult<Track> trackPsr = artistService.getTracks(artistId, page, pageSize);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, trackPsr);

            page = ParameterTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            pageSize = ParameterTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> albumPsr = artistService.getAlbums(artistId, page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, albumPsr);

            return Router.forward(PagePath.ARTIST);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
