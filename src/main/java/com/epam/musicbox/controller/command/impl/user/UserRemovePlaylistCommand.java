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

public class UserRemovePlaylistCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?command=%s&%s=%%s&%s=%%s&%s=%%s",
                    CommandType.PLAYLIST_GET_BY_ID.getName(),
                    Parameter.PLAYLIST_ID,
                    Parameter.TRACK_PAGE_INDEX,
                    Parameter.TRACK_PAGE_SIZE);

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);
            long playlistId = ParamTaker.getLong(req, Parameter.PLAYLIST_ID);

            userService.removePlaylist(userId, playlistId);

            int trackPage = ParamTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int trackPageSize = ParamTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);

            String url = String.format(REDIRECT_URL_FORMAT,
                    playlistId,
                    trackPage,
                    trackPageSize);
            return CommandResult.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
