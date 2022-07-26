package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetByLoginCommand implements Command {

    private static final String REDIRECT_USER_PAGE_URL =
            String.format("controller?%s=%s&%s=",
                    Parameter.COMMAND,
                    CommandType.USER_GET_BY_ID.getName(),
                    Parameter.USER_ID);

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            String login = req.getParameter(Parameter.LOGIN);
            String userIdStr = service.findByLogin(login)
                    .map(User::getId)
                    .map(Object::toString)
                    .orElse("");
            return Router.redirect(REDIRECT_USER_PAGE_URL + userIdStr);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
