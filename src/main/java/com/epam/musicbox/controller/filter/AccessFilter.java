package com.epam.musicbox.controller.filter;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.controller.command.CommandType;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.entity.User;
import com.epam.musicbox.service.UserService;
import com.epam.musicbox.util.Parameters;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

public class AccessFilter implements Filter {
    private static final String UNAUTHORIZED = "Unauthorized";
    private static final String PERMISSION_DENIED = "Permission denied";

    @Inject
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        Role role = Parameters.getNullable(session, Parameter.ROLE);
        if (role == null) {
            role = Role.GUEST;
        } else if (role == Role.USER || role == Role.ADMIN) {
            Long userId = Parameters.getNullable(session, Parameter.USER_ID);
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
