package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetByRoleCommand implements Command {

    private static final String COMMAND = String.format("%s&%s=",
            CommandType.USER_GET_BY_ROLE.getName(),
            Parameter.ROLE);

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            Role role = ParameterTaker.getRole(req);
            int page = ParameterTaker.getPage(req, Parameter.USER_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.USER_PAGE_SIZE);
            PageSearchResult<User> psr = userService.findByRole(role.getId(), page, pageSize);
            req.setAttribute(Parameter.USER_PAGE_SEARCH_RESULT, psr);
            req.setAttribute(Parameter.COMMAND, COMMAND + role.getName());
            return Router.forward(PagePath.USERS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
