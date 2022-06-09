package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AlbumService;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.TrackService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Services;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UserCancelLikeAlbumCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    private final AlbumService albumService = AlbumServiceImpl.getInstance();
    
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        long albumId = Parameters.getLong(req, Parameter.ALBUM_ID);
        Optional<Album> optional = albumService.findById(albumId);
        if (optional.isPresent()) {
            Album album = optional.get();
            req.setAttribute(Parameter.ALBUM, album);

            Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
            Claims body = jws.getBody();
            long userId = Parameters.getLong(body, Parameter.USER_ID);

            userService.cancelLikeAlbum(userId, albumId);
            req.setAttribute(Parameter.LIKE, false);

            Services.handlePage(req, trackService, Parameter.TRACK_PAGE, Parameter.TRACK_LIST);
        } else {
            req.setAttribute(Parameter.ALBUM, null);
            req.setAttribute(Parameter.LIKE, null);

            Services.savePageIndex(req, Parameter.TRACK_PAGE);
        }
        return CommandResult.forward(PagePath.ALBUM);
    }
}
