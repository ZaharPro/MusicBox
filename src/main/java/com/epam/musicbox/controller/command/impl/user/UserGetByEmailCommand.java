package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class UserGetByEmailCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            String email = req.getParameter(Parameter.EMAIL);
            Optional<User> optional = service.findByEmail(email);
            User user = optional.orElse(null);
            req.setAttribute(Parameter.USER, user);
            return Router.forward(PagePath.USER);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
