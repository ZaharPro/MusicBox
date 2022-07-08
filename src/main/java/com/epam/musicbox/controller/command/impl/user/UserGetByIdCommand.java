package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class UserGetByIdCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Optional<Long> optionalUserId = ParameterTaker.getOptionalLong(req, Parameter.USER_ID);
            if (optionalUserId.isPresent()) {
                long userId = optionalUserId.get();
                Optional<User> optionalUser = userService.findById(userId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    req.setAttribute(Parameter.USER, user);
                    req.setAttribute(Parameter.TRACK_COUNT, userService.countLikedTracks(userId));
                    req.setAttribute(Parameter.ALBUM_COUNT, userService.countLikedAlbums(userId));
                    req.setAttribute(Parameter.ARTIST_COUNT, userService.countLikedArtists(userId));
                    req.setAttribute(Parameter.PLAYLIST_COUNT, userService.countPlaylists(userId));
                }

                Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
                Claims body = token.getBody();
                long adminId = ParameterTaker.getLong(body, Parameter.USER_ID);

                req.setAttribute(Parameter.CAN_BAN, adminId != userId);
            }
            return Router.forward(PagePath.USER);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
