package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLikeAlbumCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        long albumId = Parameters.getLong(req, Parameter.ALBUM_ID);
        service.likeAlbum(userId, albumId);
        return CommandResult.forward(PagePath.ALBUM);
    }
}
