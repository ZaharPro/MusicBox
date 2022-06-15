package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class ArtistGetByIdCommand implements Command {

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        long artistId = ParamTaker.getLong(req, Parameter.ARTIST_ID);
        Optional<Artist> optional = artistService.findById(artistId);
        if (optional.isPresent()) {
            Artist artist = optional.get();
            req.setAttribute(Parameter.ARTIST, artist);

            Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
            Claims body = jws.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);

            boolean like = userService.isLikeArtist(userId, artistId);
            req.setAttribute(Parameter.LIKE, like);

        } else {
            req.setAttribute(Parameter.ARTIST, null);
            req.setAttribute(Parameter.LIKE, null);
        }

        int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.TRACK_PAGE_SIZE);
        List<Track> tracks = artistService.getTracks(artistId, page, pageSize);
        req.setAttribute(Parameter.TRACK_PAGE, page);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        page = ParamTaker.getPage(req, Parameter.ALBUM_PAGE);
        pageSize = ParamTaker.getInt(req, Parameter.ALBUM_PAGE_SIZE);
        List<Album> albums = artistService.getAlbums(artistId, page, pageSize);
        req.setAttribute(Parameter.ALBUM_PAGE, page);
        req.setAttribute(Parameter.ALBUM_LIST, albums);

        return CommandResult.forward(PagePath.ARTIST);
    }
}
