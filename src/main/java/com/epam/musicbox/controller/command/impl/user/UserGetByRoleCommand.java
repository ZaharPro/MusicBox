package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserGetByRoleCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        Role role = ParamTaker.getRole(req);
        int page = ParamTaker.getPage(req, Parameter.USER_PAGE);
        int pageSize = ParamTaker.getInt(req, Parameter.USER_PAGE_SIZE);
        List<User> list = userService.findByRole(role.getId(), page, pageSize);
        req.setAttribute(Parameter.USER_PAGE, page);
        req.setAttribute(Parameter.USER_LIST, list);
        return CommandResult.forward(PagePath.USERS_BY_ROLE);
    }
}
