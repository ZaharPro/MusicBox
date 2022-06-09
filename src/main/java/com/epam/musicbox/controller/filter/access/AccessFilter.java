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
                sendError(req, resp, PagePath.LOGIN, UNAUTHORIZED_MSG);
                break;
            case SESSION_TIMEOUT:
                Cookie deleteBlackToken = new Cookie(Parameter.ACCESS_TOKEN, null);
                deleteBlackToken.setMaxAge(0);
                resp.addCookie(deleteBlackToken);
                sendError(req, resp, PagePath.LOGIN, SESSION_TIMEOUT_MSG);
                break;
            case PERMISSION_DENIED:
                sendError(req, resp, PagePath.ERROR, PERMISSION_DENIED_MSG);
                break;
            case USER_BANNED:
                sendError(req, resp, PagePath.ERROR, USER_BANNED_MSG);
                break;
        }
    }

    private AccessCode checkRequest(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object invalidate = session.getAttribute(Parameter.INVALIDATE);
        if (invalidate != null) {
            session.invalidate();
            Role role = Role.GUEST;
            String commandName = req.getParameter(Parameter.COMMAND);
            return checkCommand(role, commandName);
        }
        Optional<Cookie> optionalCookie = authService.getToken(req.getCookies());
        if (optionalCookie.isEmpty()) {
            Role role = Role.GUEST;
            String commandName = req.getParameter(Parameter.COMMAND);
            return checkCommand(role, commandName);
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
            return checkCommand(role, commandName);
        } catch (ServiceException e) {
            return AccessCode.SESSION_TIMEOUT;
        }
    }

    private AccessCode checkCommand(Role role, String commandName) {
        if (commandName == null)
            return AccessCode.OK;
        CommandType commandType = CommandType.of(commandName);
        return roleRights.isExistCommandType(role, commandType) ?
                AccessCode.OK :
                AccessCode.PERMISSION_DENIED;
    }


    private void sendError(HttpServletRequest req,
                           HttpServletResponse resp,
                           String page,
                           String message) throws ServletException, IOException {
        req.setAttribute(Parameter.ERROR_MESSAGE, message);
        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }
}
