package com.epam.musicbox.controller.filter.access;

import com.epam.musicbox.controller.PagePath;
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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

/**
 * The type Access filter.
 */
public class AccessFilter implements Filter {

    private static final String SESSION_TIMEOUT_MSG = "Session timeout";
    private static final String PERMISSION_DENIED_MSG = "Permission denied";
    private static final String USER_BANNED_MSG = "User banned";

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
                req.getRequestDispatcher(PagePath.LOGIN).forward(req, resp);
                break;
            case SESSION_TIMEOUT:
                Cookie deleteBlackToken = new Cookie(Parameter.ACCESS_TOKEN, null);
                deleteBlackToken.setMaxAge(0);
                resp.addCookie(deleteBlackToken);

                req.setAttribute(Parameter.MESSAGE, SESSION_TIMEOUT_MSG);
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
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
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private AccessCode checkRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object invalidate = session.getAttribute(Parameter.INVALIDATE);
        if (invalidate != null) {
            session.invalidate();
            return checkCommand(req, Role.GUEST, AccessCode.UNAUTHORIZED);
        }
        Jws<Claims> token;
        try {
            token = authService.getToken(req);
        } catch (ServiceException e) {
            return checkCommand(req, Role.GUEST, AccessCode.UNAUTHORIZED);
        }
        try {
            Claims body = token.getBody();
            long userId = ParameterTaker.getLong(body, Parameter.USER_ID);
            Role role = ParameterTaker.getRole(body);

            UserService userService = UserServiceImpl.getInstance();
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                return AccessCode.SESSION_TIMEOUT;
            }
            User user = optionalUser.get();
            if (user.getBanned()) {
                return AccessCode.USER_BANNED;
            }
            return checkCommand(req, role, AccessCode.PERMISSION_DENIED);
        } catch (ServiceException e) {
            return AccessCode.SESSION_TIMEOUT;
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
