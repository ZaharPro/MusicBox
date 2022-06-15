package com.epam.musicbox.controller.command.impl.track;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
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
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);
        Optional<Track> optional = trackService.findById(trackId);
        if (optional.isPresent()) {
            Track track = optional.get();
            req.setAttribute(Parameter.TRACK, track);

            Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
            Claims body = jws.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);

            boolean like = userService.isLikeTrack(userId, trackId);
            req.setAttribute(Parameter.LIKE, like);
        } else {
            req.setAttribute(Parameter.TRACK, null);
            req.setAttribute(Parameter.LIKE, null);
        }

        int page = ParamTaker.getPage(req, Parameter.TRACK_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.TRACK_PAGE_SIZE);
        List<Track> tracks = trackService.findPage(page, pageSize);
        req.setAttribute(Parameter.TRACK_PAGE, page);
        req.setAttribute(Parameter.TRACK_LIST, tracks);

        return CommandResult.forward(PagePath.TRACK);
    }
}
