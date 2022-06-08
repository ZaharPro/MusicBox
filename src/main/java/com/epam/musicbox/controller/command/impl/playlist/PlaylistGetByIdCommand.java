package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class PlaylistGetByIdCommand implements Command {

    private final PlaylistService playlistService = PlaylistServiceImpl.getInstance();

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        Role role = Parameters.getRole(body);
        if (role == Role.ADMIN) {
            req.setAttribute(Parameter.ADMIN, userId);
        }
        long playlistId = Parameters.getLong(req, Parameter.PLAYLIST_ID);
        Optional<Playlist> optional = playlistService.findById(playlistId);
        if (optional.isPresent()) {
            Playlist playlist = optional.get();
            req.setAttribute(Parameter.PLAYLIST, playlist);

            boolean like = userService.hasPlaylist(userId, playlistId);
            req.setAttribute(Parameter.LIKE, like);
        } else {
            req.setAttribute(Parameter.PLAYLIST, null);
            req.setAttribute(Parameter.LIKE, null);
        }

        int trackPage = Parameters.getIntOrZero(req, Parameter.TRACK_PAGE);
        List<Track> tracks = trackService.findPage(trackPage);
        req.setAttribute(Parameter.TRACK_PAGE, trackPage);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        return CommandResult.forward(PagePath.PLAYLIST);
    }
}
