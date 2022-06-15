package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PageSearchResult;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetLikedAlbumsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParamTaker.getLong(body, Parameter.USER_ID);
            int page = ParamTaker.getPage(req, Parameter.ALBUM_PAGE_INDEX);
            int pageSize = ParamTaker.getInt(req, Parameter.ALBUM_PAGE_SIZE);
            PageSearchResult<Album> pageSearchResult = service.getLikedAlbums(userId, page, pageSize);
            req.setAttribute(Parameter.ALBUM_PAGE_SEARCH_RESULT, pageSearchResult);
            return CommandResult.forward(PagePath.ALBUMS);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }

}
