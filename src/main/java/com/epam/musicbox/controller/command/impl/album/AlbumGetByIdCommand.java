package com.epam.musicbox.controller.command.impl.album;

import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Album;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.*;
import com.epam.musicbox.service.impl.AlbumServiceImpl;
import com.epam.musicbox.service.impl.TrackServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import com.epam.musicbox.util.Commands;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class AlbumGetByIdCommand implements Command {

    private final AlbumService albumService = AlbumServiceImpl.getInstance();

    private final UserService userService = UserServiceImpl.getInstance();

    private final TrackService trackService = TrackServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);

        long albumId = Parameters.getLong(req, Parameter.ALBUM_ID);
        Optional<Album> optional = albumService.findById(albumId);
        if (optional.isPresent()) {
            Album album = optional.get();
            req.setAttribute(Parameter.ALBUM, album);

            boolean like = userService.isLikeAlbum(userId, albumId);
            req.setAttribute(Parameter.LIKE, like);
        } else {
            req.setAttribute(Parameter.ALBUM, null);
            req.setAttribute(Parameter.LIKE, null);
        }
        Commands.handlePage(req, trackService, Parameter.TRACK_PAGE, Parameter.TRACK_LIST);

        return CommandResult.forward(PagePath.ALBUM);
    }
}
