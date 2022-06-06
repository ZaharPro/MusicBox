package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class UserLikeAlbumCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        long albumId = Parameters.getLong(req, Parameter.ALBUM_ID);
        userService.likeAlbum(userId, albumId);

        Optional<Album> optionalTrack = albumService.findById(albumId);
        if (optionalTrack.isEmpty()) {
            throw new ServiceException("Artist not found");
        }
        Album album = optionalTrack.get();
        req.setAttribute(Parameter.TRACK, album);

        int trackPage = Parameters.getIntOrZero(req, Parameter.TRACK_PAGE);
        List<Track> list = trackService.findPage(trackPage);
        req.setAttribute(Parameter.TRACK_PAGE, trackPage);
        req.setAttribute(Parameter.TRACK_LIST, list);

        return CommandResult.forward(PagePath.ALBUM);
    }
}
