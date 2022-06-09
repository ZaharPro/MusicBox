package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

public class TrackGetByIdCommand implements Command {

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        Role role = Parameters.getRole(body);
        if (role == Role.ADMIN) {
            req.setAttribute(Parameter.ADMIN, userId);
        }
        long trackId = Parameters.getLong(req, Parameter.TRACK_ID);
        Optional<Track> optional = trackService.findById(trackId);
        if (optional.isPresent()) {
            Track track = optional.get();
            req.setAttribute(Parameter.TRACK, track);

            boolean like = userService.isLikeTrack(userId, trackId);
            req.setAttribute(Parameter.LIKE, like);
        } else {
            req.setAttribute(Parameter.TRACK, null);
            req.setAttribute(Parameter.LIKE, null);
        }

        int trackPage = Parameters.getIntOrZero(req, Parameter.TRACK_PAGE);
        List<Track> list = trackService.findPage(trackPage);
        req.setAttribute(Parameter.TRACK_PAGE, trackPage);
        req.setAttribute(Parameter.TRACK_LIST, list);

        return CommandResult.forward(PagePath.TRACK);
    }
}
