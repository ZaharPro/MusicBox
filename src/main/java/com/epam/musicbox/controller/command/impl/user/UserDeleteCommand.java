package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.ObjectUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserDeleteCommand implements Command {
    @Inject
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        String userIdString = req.getParameter(Parameter.USER_ID);
        Integer userId = ObjectUtils.parseInt(userIdString);
        userService.deleteById(userId);
    }
}
