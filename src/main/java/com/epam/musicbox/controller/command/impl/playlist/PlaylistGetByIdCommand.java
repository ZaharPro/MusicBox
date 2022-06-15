package com.epam.musicbox.controller.command.impl.playlist;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PlaylistService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.PlaylistServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
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
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        long playlistId = ParamTaker.getLong(req, Parameter.PLAYLIST_ID);
        Optional<Playlist> optional = playlistService.findById(playlistId);
        if (optional.isPresent()) {
            Playlist playlist = optional.get();
            req.setAttribute(Parameter.PLAYLIST, playlist);

            Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
            Claims body = jws.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);

            boolean like = userService.hasPlaylist(userId, playlistId);
            req.setAttribute(Parameter.LIKE, like);
        } else {
            req.setAttribute(Parameter.PLAYLIST, null);
            req.setAttribute(Parameter.LIKE, null);
        }

        int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.TRACK_PAGE_SIZE);
        List<Track> tracks = trackService.findPage(page, pageSize);
        req.setAttribute(Parameter.TRACK_PAGE, page);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        return CommandResult.forward(PagePath.PLAYLIST);
    }
}
