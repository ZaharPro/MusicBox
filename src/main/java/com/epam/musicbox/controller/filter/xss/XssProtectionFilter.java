package com.epam.musicbox.controller.filter.xss;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class XssProtectionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        XssRequestWrapper requestWrapper = new XssRequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}