package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Artist;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetLikedArtistsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
        Claims body = jws.getBody();
        long userId = ParamTaker.getLong(body, Parameter.USER_ID);
        int page = ParamTaker.getPage(req, Parameter.ARTIST_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.ARTIST_PAGE_SIZE);
        List<Artist> artists = service.getLikedArtists(userId, page, pageSize);
        req.setAttribute(Parameter.ARTIST_PAGE, page);
        req.setAttribute(Parameter.ARTIST_LIST, artists);
        return CommandResult.forward(PagePath.ARTISTS);
    }
}
