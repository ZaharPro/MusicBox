package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.ArtistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class UserCancelLikeArtistCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    private final ArtistService artistService = ArtistServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        long artistId = Parameters.getLong(req, Parameter.ARTIST_ID);
        userService.cancelLikeArtist(userId, artistId);

        Optional<Artist> optionalArtist = artistService.findById(artistId);
        if (optionalArtist.isEmpty()) {
            throw new ServiceException("Artist not found");
        }
        Artist artist = optionalArtist.get();
        req.setAttribute(Parameter.ARTIST, artist);

        int trackPage = Parameters.getIntOrZero(req, Parameter.TRACK_PAGE);
        List<Track> tracks = trackService.findPage(trackPage);
        req.setAttribute(Parameter.TRACK_PAGE, trackPage);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        int albumPage = Parameters.getIntOrZero(req, Parameter.ALBUM_PAGE);
        List<Album> albums = albumService.findPage(albumPage);
        req.setAttribute(Parameter.ALBUM_PAGE, albumPage);
        req.setAttribute(Parameter.ALBUM_LIST, albums);

        return CommandResult.forward(PagePath.ARTIST);
    }
}
