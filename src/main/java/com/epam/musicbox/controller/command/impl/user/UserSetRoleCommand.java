package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserSetRoleCommand implements Command {
    @Inject
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        HttpSession session = req.getSession();
        Integer userId = ((Integer) session.getAttribute(Parameter.USER_ID));
        String roleName = req.getParameter(Parameter.ROLE_NAME);
        Role role = Role.findByName(roleName);
        if (role == null)
            throw new HttpException("Invalid role", HttpServletResponse.SC_BAD_REQUEST);
        int roleId = role.getId();
        userService.cancelLikeTrack(userId, roleId);
    }
}
