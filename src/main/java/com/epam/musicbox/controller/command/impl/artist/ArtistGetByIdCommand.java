package com.epam.musicbox.controller.command.impl.artist;

import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
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
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        Role role = Parameters.getRole(body);
        if (role == Role.ADMIN) {
            req.setAttribute(Parameter.ADMIN, userId);
        }
        long artistId = Parameters.getLong(req, Parameter.ARTIST_ID);
        Optional<Artist> optional = artistService.findById(artistId);
        if (optional.isPresent()) {
            Artist artist = optional.get();
            req.setAttribute(Parameter.ARTIST, artist);

            boolean like = userService.isLikeArtist(userId, artistId);
            req.setAttribute(Parameter.LIKE, like);

        } else {
            req.setAttribute(Parameter.ARTIST, null);
            req.setAttribute(Parameter.LIKE, null);
        }

        int trackPage = Parameters.getIntOrZero(req, Parameter.TRACK_PAGE);
        List<Track> tracks = artistService.getTracks(artistId, trackPage);
        req.setAttribute(Parameter.TRACK_PAGE, trackPage);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        int albumPage = Parameters.getIntOrZero(req, Parameter.ALBUM_PAGE);
        List<Album> albums = artistService.getAlbums(artistId, albumPage);
        req.setAttribute(Parameter.ALBUM_LIST, albumPage);
        req.setAttribute(Parameter.ALBUM_LIST, albums);

        return CommandResult.forward(PagePath.ARTIST);
    }
}
