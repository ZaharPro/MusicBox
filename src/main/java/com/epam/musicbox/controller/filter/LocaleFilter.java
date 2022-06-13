package com.epam.musicbox.controller.filter;


import com.epam.musicbox.util.constant.Parameter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

    public static final String DEFAULT_LOCALE = "en_EN";

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (session.getAttribute(Parameter.LOCALE) == null) {
            session.setAttribute(Parameter.LOCALE, DEFAULT_LOCALE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
