package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Track;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetLikedTracksCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long userId = Services.getUserIdFromReqOrJws(req);
        int page = Parameters.getIntOrZero(req, Parameter.TRACK_PAGE);
        List<Track> list = service.getLikedTracks(userId, page);
        req.setAttribute(Parameter.TRACK_PAGE, page);
        req.setAttribute(Parameter.TRACK_LIST, list);
        return CommandResult.forward(PagePath.TRACKS);
    }
}
