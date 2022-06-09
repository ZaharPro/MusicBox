package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UserLikeTrackCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long trackId = Parameters.getLong(req, Parameter.TRACK_ID);
        Optional<Track> optional = trackService.findById(trackId);
        if (optional.isPresent()) {
            Track track = optional.get();
            req.setAttribute(Parameter.TRACK, track);

            Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
            Claims body = jws.getBody();
            long userId = Parameters.getLong(body, Parameter.USER_ID);

            userService.likeTrack(userId, trackId);
            req.setAttribute(Parameter.LIKE, true);
        } else {
            req.setAttribute(Parameter.TRACK, null);
            req.setAttribute(Parameter.LIKE, null);
        }
        return CommandResult.forward(PagePath.TRACK);
    }
}
