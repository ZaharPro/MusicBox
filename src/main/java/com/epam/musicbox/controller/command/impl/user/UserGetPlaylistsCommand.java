package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Playlist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetPlaylistsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            int page = ParameterTaker.getPage(req, Parameter.PLAYLIST_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.PLAYLIST_PAGE_SIZE);
            PageSearchResult<Playlist> psr = service.getPlaylists(userId, page, pageSize);
            req.setAttribute(Parameter.PLAYLIST_PAGE_SEARCH_RESULT, psr);
            req.setAttribute(Parameter.COMMAND, CommandType.USER_GET_PLAYLISTS.getName());
            return Router.forward(PagePath.PLAYLISTS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
