package com.epam.musicbox.controller.filter.access;

import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.util.constant.PagePath;
import com.epam.musicbox.util.constant.Parameter;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

public class AccessFilter implements Filter {

    private static final String UNAUTHORIZED_MSG = "Unauthorized";
    private static final String SESSION_TIMEOUT_MSG = "Session timeout";
    private static final String PERMISSION_DENIED_MSG = "Permission denied";
    private static final String USER_BANNED_MSG = "User banned";
    private static final String COMMAND_NOT_FOUND_MSG = "Command not found";

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
                req.setAttribute(Parameter.ERROR_MESSAGE, UNAUTHORIZED_MSG);
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                break;
            case SESSION_TIMEOUT:
                Cookie deleteBlackToken = new Cookie(Parameter.ACCESS_TOKEN, null);
                deleteBlackToken.setMaxAge(0);
                resp.addCookie(deleteBlackToken);
                req.setAttribute(Parameter.ERROR_MESSAGE, SESSION_TIMEOUT_MSG);
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                break;
            case PERMISSION_DENIED:
                req.setAttribute(Parameter.ERROR_MESSAGE, PERMISSION_DENIED_MSG);
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                break;
            case USER_BANNED:
                req.setAttribute(Parameter.ERROR_MESSAGE, USER_BANNED_MSG);
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                break;
            case NOT_FOUND:
                req.setAttribute(Parameter.ERROR_MESSAGE, COMMAND_NOT_FOUND_MSG);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private AccessCode checkRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object invalidate = session.getAttribute(Parameter.INVALIDATE);
        if (invalidate != null) {
            session.invalidate();
            String commandName = req.getParameter(Parameter.COMMAND);
            return checkCommand(Role.GUEST, commandName, AccessCode.UNAUTHORIZED);
        }
        Optional<Cookie> optionalCookie = authService.getToken(req.getCookies());
        if (optionalCookie.isEmpty()) {
            String commandName = req.getParameter(Parameter.COMMAND);
            return checkCommand(Role.GUEST, commandName, AccessCode.UNAUTHORIZED);
        }
        try {
            String token = optionalCookie.get().getValue();
            Jws<Claims> claims = authService.getJws(token);
            Claims body = claims.getBody();

            long userId = Parameters.getLong(body, Parameter.USER_ID);
            Role role = Parameters.getRole(body);

            UserService userService = UserServiceImpl.getInstance();
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                return AccessCode.SESSION_TIMEOUT;
            }
            User user = optionalUser.get();
            if (user.getBanned()) {
                return AccessCode.USER_BANNED;
            }
            String commandName = req.getParameter(Parameter.COMMAND);
            return checkCommand(role, commandName, AccessCode.PERMISSION_DENIED);
        } catch (ServiceException e) {
            return AccessCode.SESSION_TIMEOUT;
        }
    }

    private AccessCode checkCommand(Role role, String commandName, AccessCode failCode) {
        if (commandName == null)
            return AccessCode.OK;
        CommandType commandType = CommandType.of(commandName);
        if (commandType == null) {
            return AccessCode.NOT_FOUND;
        }
        return roleRights.isExistCommandType(role, commandType) ?
                AccessCode.OK :
                failCode;
    }
}
