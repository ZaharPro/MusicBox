package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class UserGetByLoginCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            String login = req.getParameter(Parameter.LOGIN);
            Optional<User> optionalUser = service.findByLogin(login);
            User user = optionalUser.orElse(null);
            req.setAttribute(Parameter.USER, user);
            return CommandResult.forward(PagePath.USER);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
