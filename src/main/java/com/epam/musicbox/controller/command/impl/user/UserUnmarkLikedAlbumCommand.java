package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class UserUnmarkLikedAlbumCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?%s=%s&%s=%%s&%s=%%s&%s=%%s",
                    Parameter.COMMAND,
                    CommandType.ALBUM_GET_BY_ID.getName(),
                    Parameter.ALBUM_ID,
                    Parameter.TRACK_PAGE_INDEX,
                    Parameter.TRACK_PAGE_SIZE);

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            long albumId = ParameterTaker.getLong(req, Parameter.ALBUM_ID);

            userService.unmarkLikedAlbum(userId, albumId);

            int trackPage = ParameterTaker.getPage(req, Parameter.TRACK_PAGE_INDEX);
            int trackPageSize = ParameterTaker.getPageSize(req, Parameter.TRACK_PAGE_SIZE);

            String url = String.format(REDIRECT_URL_FORMAT,
                    albumId,
                    trackPage,
                    trackPageSize);
            return Router.redirect(url);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
