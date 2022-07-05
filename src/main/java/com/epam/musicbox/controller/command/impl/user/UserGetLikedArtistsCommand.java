package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetLikedArtistsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            int page = ParameterTaker.getPage(req, Parameter.ARTIST_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.ARTIST_PAGE_SIZE);
            PageSearchResult<Artist> pageSearchResult = service.getLikedArtists(userId, page, pageSize);
            req.setAttribute(Parameter.ARTIST_PAGE_SEARCH_RESULT, pageSearchResult);
            req.setAttribute(Parameter.COMMAND, CommandType.USER_GET_LIKED_ARTISTS.getName());
            return Router.forward(PagePath.ARTISTS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
