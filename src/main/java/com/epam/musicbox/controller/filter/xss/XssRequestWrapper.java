package com.epam.musicbox.controller.filter.xss;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.regex.Pattern;

/**
 * The type Xss request wrapper removing tags.
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private static final Pattern tagPattern = Pattern.compile("[<>]");

    /**
     * Instantiates a new Xss request wrapper.
     *
     * @param request the http request
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null)
            return null;
        for (int i = 0, l = values.length; i < l; i++) {
            values[i] = stripXss(values[i]);
        }
        return values;
    }

    @Override
    public String getParameter(String parameter) {
        return stripXss(super.getParameter(parameter));
    }

    @Override
    public String getHeader(String name) {
        return stripXss(super.getHeader(name));
    }

    private String stripXss(String value) {
        if (value == null)
            return null;
        return tagPattern.matcher(value).replaceAll("");
    }
}
