package com.epam.musicbox.controller.filter;

import com.epam.musicbox.constant.PagePath;
import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.service.impl.UserServiceImpl;
import com.epam.musicbox.util.AuthUtils;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class AccessFilter implements Filter {
    private static final String UNAUTHORIZED = "Unauthorized";
    private static final String PERMISSION_DENIED = "Permission denied";
    private static final String SESSION_TIMEOUT = "Session timeout";
    private static final String USER_BANNED = "User banned";

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Role role;
        Optional<Cookie> optionalCookie = AuthUtils.getTokenFromCookies(req.getCookies());
        if (optionalCookie.isEmpty()) {
            role = Role.GUEST;
        } else {
            Jws<Claims> claims;
            try {
                String token = optionalCookie.get().getValue();
                claims = AuthUtils.getClaimsFromToken(token);
            } catch (Exception e) {
                Cookie deleteBlackToken = new Cookie(Parameter.ACCESS_TOKEN, null);
                deleteBlackToken.setMaxAge(0);
                resp.addCookie(deleteBlackToken);
                redirectToLogin(req, resp, SESSION_TIMEOUT);
                return;
            }
            Claims body = claims.getBody();
            Long userId = Parameters.getNullable(body, Parameter.USER_ID);
            role = Parameters.getNullable(body, Parameter.ROLE);
            if (userId == null) {
                redirectToLogin(req, resp, UNAUTHORIZED);
                return;
            }
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                redirectToLogin(req, resp, UNAUTHORIZED);
                return;
            }
            User user = optionalUser.get();
            if (user.getBanned()) {
                sendForbiddenError(req, resp, USER_BANNED);
                return;
            }
        }

        String commandName = req.getParameter(Parameter.COMMAND);
        if (commandName != null) {
            CommandType commandType = CommandType.of(commandName);
            if (!role.isExistCommandType(commandType)) {
                sendForbiddenError(req, resp, PERMISSION_DENIED);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void redirectToLogin(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 String message) throws ServletException, IOException {
        sendError(req, message, PagePath.LOGIN, resp);
    }

    private void sendForbiddenError(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    String message) throws IOException, ServletException {
        message += ", statusCode = " + HttpServletResponse.SC_FORBIDDEN;
        sendError(req, message, PagePath.ERROR, resp);
    }

    private void sendError(HttpServletRequest req,
                           String message,
                           String error,
                           HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Parameter.ERROR_MESSAGE, message);
        RequestDispatcher dispatcher = req.getRequestDispatcher(error);
        dispatcher.forward(req, resp);
    }
}
