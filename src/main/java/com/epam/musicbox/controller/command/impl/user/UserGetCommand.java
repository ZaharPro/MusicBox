package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.Router;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.page.PageSearchResult;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest req) throws CommandException {
        try {
            int page = ParameterTaker.getPage(req, Parameter.USER_PAGE_INDEX);
            int pageSize = ParameterTaker.getPageSize(req, Parameter.USER_PAGE_SIZE);
            PageSearchResult<User> pageSearchResult = userService.findPage(page, pageSize);
            req.setAttribute(Parameter.USER_PAGE_SEARCH_RESULT, pageSearchResult);
            return Router.forward(PagePath.USERS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
