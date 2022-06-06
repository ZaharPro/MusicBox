package com.epam.musicbox.controller.command.impl.page;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandResult;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class GoToUserPage extends GoToPageCommand {

    private final UserService userService = UserServiceImpl.getInstance();

    public GoToUserPage() {
        super(PagePath.USER);
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
        Claims body = jws.getBody();
        long userId = Parameters.getLong(body, Parameter.USER_ID);

        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ServiceException("User not found");
        }
        User user = optionalUser.get();
        req.setAttribute(Parameter.USER, user);

        Role role = Parameters.getRole(body);
        if (role == Role.ADMIN) {
            req.setAttribute(Parameter.ADMIN, "admin");
        }

        return super.execute(req, resp);
    }
}
