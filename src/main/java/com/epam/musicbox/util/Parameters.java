package com.epam.musicbox.util;

import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.function.Function;

public class Parameters {
    private static final Function<String, Long> LONG_MAPPER = Long::parseLong;
    private static final Function<String, Boolean> BOOLEAN_MAPPER = Parameters::parseBoolean;

    private static Boolean parseBoolean(String s) {
        if (s == null)
            return null;
        return switch (s) {
            case "true" -> Boolean.TRUE;
            case "false" -> Boolean.FALSE;
            default -> null;
        };
    }

    public static <T> T getNullable(HttpSession session, String parameterName) {
        return (T) session.getAttribute(parameterName);
    }

    public static <T> T get(HttpSession session, String paramName) throws HttpException {
        T value = getNullable(session, paramName);
        if (value == null) {
            throw new HttpException("Invalid session attribute: " + paramName, HttpServletResponse.SC_BAD_REQUEST);
        }
        return value;
    }

    public static <T> T getNullable(HttpServletRequest req,
                                    String paramName,
                                    Function<String, T> function) throws HttpException {
        String parameter = req.getParameter(paramName);
        if (parameter == null || "null".equals(parameter)) {
            return null;
        }
        try {
            return function.apply(parameter);
        } catch (Throwable e) {
            throw new HttpException("Invalid parameter: " + paramName, HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public static <T> T get(HttpServletRequest req,
                            String paramName,
                            Function<String, T> function) throws HttpException {
        T value = getNullable(req, paramName, function);
        if (value == null) {
            throw new HttpException("Invalid parameter: " + paramName, HttpServletResponse.SC_BAD_REQUEST);
        }
        return value;
    }


    public static Long getNullableLong(HttpServletRequest req, String paramName) throws HttpException {
        return getNullable(req, paramName, LONG_MAPPER);
    }

    public static long getLong(HttpServletRequest req, String paramName) throws HttpException {
        return get(req, paramName, LONG_MAPPER);
    }

    public static Boolean getNullableBoolean(HttpServletRequest req, String paramName) throws HttpException {
        return getNullable(req, paramName, BOOLEAN_MAPPER);
    }

    public static boolean getBoolean(HttpServletRequest req, String paramName) throws HttpException {
        return get(req, paramName, BOOLEAN_MAPPER);
    }
}
