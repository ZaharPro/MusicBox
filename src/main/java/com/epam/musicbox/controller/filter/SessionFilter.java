package com.epam.musicbox.controller.filter;


import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.controller.ParameterTaker;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * The type Locale filter initialized locale.
 */
@WebFilter(urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    private static final String DEFAULT_LOCALE = "en_EN";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setAttribute(Parameter.LOCALE, DEFAULT_LOCALE);
            try {
                Jws<Claims> token = AuthServiceImpl.getInstance().getToken(req);
                Claims body = token.getBody();

                String login = (String) body.get(Parameter.LOGIN);
                session.setAttribute(Parameter.LOGIN, login);

                Role role = ParameterTaker.getRole(body);
                session.setAttribute(Parameter.ROLE, role.getName());
            } catch (ServiceException e) {
                session.setAttribute(Parameter.ROLE, Role.GUEST.getName());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
