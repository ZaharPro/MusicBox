package com.epam.musicbox.controller.filter.access;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class AccessFilter implements Filter {

    private static final String PERMISSION_DENIED_MSG = "Permission denied";
    private static final String USER_BANNED_MSG = "User banned";
    private static final String COMMAND_NOT_FOUND_MSG = "Command not found";

    private static final String REDIRECT_URL =
            String.format("controller?%s=%s",
                    Parameter.COMMAND,
                    CommandType.LOGIN_PAGE.getName());
    private final AuthService authService = AuthServiceImpl.getInstance();
    private final RoleRights roleRights = RoleRights.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        AccessCode code = checkRequest(req);
        switch (code) {
            case OK:
                filterChain.doFilter(req, resp);
                break;
            case UNAUTHORIZED:
                Cookie blackToken = new Cookie(Parameter.ACCESS_TOKEN, null);
                blackToken.setMaxAge(0);
                req.getSession().invalidate();
                resp.addCookie(blackToken);
                resp.sendRedirect(REDIRECT_URL);
                break;
            case PERMISSION_DENIED:
                req.setAttribute(Parameter.MESSAGE, PERMISSION_DENIED_MSG);
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                break;
            case USER_BANNED:
                req.setAttribute(Parameter.MESSAGE, USER_BANNED_MSG);
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                break;
            case NOT_FOUND:
                req.setAttribute(Parameter.MESSAGE, COMMAND_NOT_FOUND_MSG);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private AccessCode checkRequest(HttpServletRequest req) {
        Jws<Claims> token;
        try {
            token = authService.getToken(req);
        } catch (ServiceException e) {
            return checkCommand(req, Role.GUEST, AccessCode.UNAUTHORIZED);
        }
        try {
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);

            UserService userService = UserServiceImpl.getInstance();
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                return AccessCode.UNAUTHORIZED;
            }
            User user = optionalUser.get();
            if (user.getBanned()) {
                return AccessCode.USER_BANNED;
            }
            return checkCommand(req, user.getRole(), AccessCode.PERMISSION_DENIED);
        } catch (ServiceException e) {
            return AccessCode.UNAUTHORIZED;
        }
    }

    private AccessCode checkCommand(HttpServletRequest req, Role role, AccessCode failCode) {
        String commandName = req.getParameter(Parameter.COMMAND);
        if (commandName == null)
            return AccessCode.OK;
        CommandType commandType = CommandType.findByName(commandName);
        if (commandType == null) {
            return AccessCode.NOT_FOUND;
        }
        return roleRights.isExistCommandType(role, commandType) ?
                AccessCode.OK :
                failCode;
    }
}
