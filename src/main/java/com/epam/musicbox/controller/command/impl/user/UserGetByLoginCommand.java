package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UserGetByLoginCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        String login = req.getParameter(Parameter.LOGIN);
        Optional<User> optionalUser = service.findByLogin(login);
        User user = optionalUser.orElse(null);
        req.setAttribute(Parameter.OBJECT, user);
        return CommandResult.forward(PagePath.USER);
    }
}
