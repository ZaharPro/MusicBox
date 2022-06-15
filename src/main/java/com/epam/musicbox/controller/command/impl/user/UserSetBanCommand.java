package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UserSetBanCommand implements Command {

    private static final String REDIRECT_URL_FORMAT =
            String.format("controller?command=%s&%s=%%s",
                    CommandType.USER_GET_BY_ID.getName(),
                    Parameter.USER_ID);

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        long userId = ParamTaker.getLong(req, Parameter.USER_ID);
        boolean banned = ParamTaker.getBoolean(req, Parameter.BANNED);

        banUser(userId, banned);

        String url = String.format(REDIRECT_URL_FORMAT, userId);
        return CommandResult.redirect(url);
    }

    private void banUser(long userId, boolean banned) throws ServiceException {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (banned == user.getBanned())
                return;
            user.setBanned(banned);
            userService.save(user);
        }
    }
}
