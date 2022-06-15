package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class UserUnmarkLikedTrackCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?command=%s&%s=%%s",
                    CommandType.TRACK_GET_BY_ID.getName(),
                    Parameter.TRACK_ID);

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);
            long trackId = ParamTaker.getLong(req, Parameter.TRACK_ID);

            userService.unmarkLikedTrack(userId, trackId);

            String url = String.format(REDIRECT_URL_FORMAT, trackId);
            return CommandResult.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
