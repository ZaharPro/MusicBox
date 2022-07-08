package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
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

public class ProfilePageCommand extends PageCommand {

    private static final String USER_NOT_FOUND_MSG = "User not found";

    private final UserService userService = UserServiceImpl.getInstance();

    public ProfilePageCommand() {
        super(PagePath.PROFILE);
    }

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);

            User user = userService.findById(userId)
                    .orElseThrow(() -> new CommandException(USER_NOT_FOUND_MSG));
            req.setAttribute(Parameter.USER, user);
            req.setAttribute(Parameter.TRACK_COUNT, userService.countLikedTracks(userId));
            req.setAttribute(Parameter.ALBUM_COUNT, userService.countLikedAlbums(userId));
            req.setAttribute(Parameter.ARTIST_COUNT, userService.countLikedArtists(userId));
            req.setAttribute(Parameter.PLAYLIST_COUNT, userService.countPlaylists(userId));
            return super.execute(req);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
