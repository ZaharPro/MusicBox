package com.epam.musicbox.controller.filter;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.AuthUtils;
import com.epam.musicbox.util.Parameters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.inject.Inject;
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

    @Inject
    private UserService userService;

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
                sendError(req, resp, SESSION_TIMEOUT, HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Claims body = claims.getBody();
            Long userId = Parameters.getNullable(body, Parameter.USER_ID);
            role = Parameters.getNullable(body, Parameter.ROLE);
            if (userId == null) {
                sendError(req, resp, UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Optional<User> optionalUser = userService.findById(userId);
            if (optionalUser.isEmpty()) {
                sendError(req, resp, UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            User user = optionalUser.get();
            if (user.getBanned()) {
                sendError(req, resp, PERMISSION_DENIED, HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        String commandName = req.getParameter(Parameter.COMMAND);
        CommandType commandType = CommandType.of(commandName);
        if (!role.isExistCommandType(commandType)) {
            sendError(req, resp, PERMISSION_DENIED, HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void sendError(HttpServletRequest request,
                           HttpServletResponse response,
                           String message,
                           int statusCode) throws IOException {
        request.setAttribute(Parameter.ERROR_MESSAGE, message);
        response.sendError(statusCode);
    }
}
