package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.PageSearchResult;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetByRoleCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            Role role = ParamTaker.getRole(req);
            int page = ParamTaker.getPage(req, Parameter.USER_PAGE_INDEX);
            int pageSize = ParamTaker.getPageSize(req, Parameter.USER_PAGE_SIZE);
            PageSearchResult<User> pageSearchResult = userService.findByRole(role.getId(), page, pageSize);
            req.setAttribute(Parameter.USER_PAGE_SEARCH_RESULT, pageSearchResult);
            return CommandResult.forward(PagePath.USERS_BY_ROLE);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
