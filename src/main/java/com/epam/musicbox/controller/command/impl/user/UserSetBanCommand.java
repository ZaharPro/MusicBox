package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class UserSetBanCommand implements Command {
    @Inject
    private UserService userService;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws HttpException {
        HttpSession session = req.getSession();
        Integer userId = ((Integer) session.getAttribute(Parameter.USER_ID));
        String bannedString = req.getParameter(Parameter.ROLE_NAME);
        boolean banned = Boolean.parseBoolean(bannedString);
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (banned == user.getBanned())
                return;
            user.setBanned(banned);
            userService.save(user);
        }
    }
}
