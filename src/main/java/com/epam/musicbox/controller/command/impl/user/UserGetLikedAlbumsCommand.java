package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetLikedAlbumsCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Long userId = Parameters.getNullableLong(req, Parameter.USER_ID);
        if (userId == null) {
            Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
            Claims body = jws.getBody();
            userId = Parameters.getLong(body, Parameter.USER_ID);
        }
        int page = Parameters.getIntOrZero(req, Parameter.PAGE);
        List<Album> list = service.getLikedAlbums(userId, page);
        req.setAttribute(Parameter.ALBUM_LIST, list);
        return CommandResult.forward(PagePath.ALBUMS);
    }
}
