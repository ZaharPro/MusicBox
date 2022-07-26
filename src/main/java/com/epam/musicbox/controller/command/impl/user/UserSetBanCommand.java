package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class UserSetBanCommand implements Command {

    private static final String USER_NOT_FOUND = "User not found";

    private static final String REDIRECT_URL =
            String.format("controller?%s=%s&%s=",
                    Parameter.COMMAND,
                    CommandType.USER_GET_BY_ID.getName(),
                    Parameter.USER_ID);

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            long userId = ParameterTaker.getLong(req, Parameter.USER_ID);
            boolean banned = ParameterTaker.getBoolean(req, Parameter.BANNED);

            banUser(userId, banned);

            return Router.redirect(REDIRECT_URL + userId);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    private void banUser(long userId, boolean banned) throws ServiceException {
        User user = userService.findById(userId)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));
        if (banned == user.getBanned())
            return;
        user.setBanned(banned);
        userService.save(user);
    }
}
