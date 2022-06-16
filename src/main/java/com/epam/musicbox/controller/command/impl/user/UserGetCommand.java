package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.controller.PagePath;
import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.CommandException;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.psr.PageSearchResult;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.psr.UserPageSearchResult;
import com.epam.musicbox.util.ParamTaker;
import jakarta.servlet.http.HttpServletRequest;

public class UserGetCommand implements Command {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req) throws CommandException {
        try {
            int page = ParamTaker.getPage(req, Parameter.USER_PAGE_INDEX);
            int pageSize = ParamTaker.getPageSize(req, Parameter.USER_PAGE_SIZE);
            PageSearchResult<User> pageSearchResult = userService.findPage(page, pageSize);
            UserPageSearchResult userPageSearchResult = UserPageSearchResult.from(pageSearchResult, userService);
            req.setAttribute(Parameter.USER_PAGE_SEARCH_RESULT, userPageSearchResult);
            return CommandResult.forward(PagePath.USERS);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
