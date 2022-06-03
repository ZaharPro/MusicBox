package com.epam.musicbox.controller.command.impl.user;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.Command;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class UserSetBanCommand implements Command {

    private final UserService service = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);
        boolean banned = Parameters.getBoolean(req, Parameter.BANNED);
        Optional<User> optionalUser = service.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (banned == user.getBanned())
                return CommandResult.forward(PagePath.EDIT_USER);
            user.setBanned(banned);
            service.save(user);
        }
        return CommandResult.forward(PagePath.EDIT_USER);
    }
}
