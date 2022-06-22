package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
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
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class ArtistGetByIdCommand implements Command {

    private static final String ARTIST_NOT_FOUND_MSG = "Artist not found";

    private final ArtistService artistService = ArtistServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);
            long artistId = ParamTaker.getLong(req, Parameter.ARTIST_ID);

            boolean like = userService.isLikedArtist(userId, artistId);
            req.setAttribute(Parameter.LIKE, like);

            Artist artist = artistService.findById(artistId)
                    .orElseThrow(() -> new CommandException(ARTIST_NOT_FOUND_MSG));
            req.setAttribute(Parameter.ARTIST, artist);

            int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int pageSize = ParamTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);
            PageSearchResult<Track> trackPageSearchResult = artistService.getTracks(artistId, page, pageSize);
            req.setAttribute(Parameter.TRACK_PAGE_SEARCH_RESULT, trackPageSearchResult);

            page = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            pageSize = ParamTaker.getPageSize(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> albumPageSearchResult = artistService.getAlbums(artistId, page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, albumPageSearchResult);

            return CommandResult.forward(PagePath.ARTIST);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
