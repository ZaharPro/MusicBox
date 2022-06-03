package com.epam.musicbox.util;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.function.Function;

public final class Parameters {
    private static final Function<String, Integer> INT_MAPPER = Integer::parseInt;
    private static final Function<String, Long> LONG_MAPPER = Long::parseLong;
    private static final Function<String, Boolean> BOOLEAN_MAPPER = Boolean::parseBoolean;
    private static final Function<String, Role> ROLE_MAPPER = Role::findByName;

    private Parameters() {
    }

    public static <T> T getNullable(Claims body,
                                    String paramName,
                                    Function<String, T> function) {
        String parameter = String.valueOf(body.get(paramName));
        if ("null".equals(parameter))
            return null;
        try {
            return function.apply(parameter);
        } catch (Throwable e) {
            return null;
        }
    }

    public static <T> T get(Claims body,
                            String paramName,
                            Function<String, T> function) throws ServiceException {
        T value = Parameters.getNullable(body, paramName, function);
        if (value == null)
            throw new ServiceException("Invalid body attribute: " + paramName);
        return value;
    }

    public static long getLong(Claims body,
                               String paramName) throws ServiceException {
        return Parameters.get(body, paramName, LONG_MAPPER);
    }

    public static Role getRole(Claims body) throws ServiceException {
        return Parameters.get(body, Parameter.ROLE, ROLE_MAPPER);
    }

    public static <T> T getNullable(HttpServletRequest req,
                                    String paramName,
                                    Function<String, T> function) throws ServiceException {
        String parameter = req.getParameter(paramName);
        if (parameter == null || "null".equals(parameter))
            return null;
        try {
            return function.apply(parameter);
        } catch (Throwable e) {
            throw new ServiceException("Invalid parameter: " + paramName);
        }
    }

    public static <T> T get(HttpServletRequest req,
                            String paramName,
                            Function<String, T> function) throws ServiceException {
        T value = Parameters.getNullable(req, paramName, function);
        if (value == null)
            throw new ServiceException("Invalid parameter: " + paramName);
        return value;
    }

    public static Integer getNullableInt(HttpServletRequest req, String paramName) throws ServiceException {
        return Parameters.getNullable(req, paramName, INT_MAPPER);
    }

    public static int getInt(HttpServletRequest req, String paramName) throws ServiceException {
        return Parameters.get(req, paramName, INT_MAPPER);
    }

    public static Long getNullableLong(HttpServletRequest req, String paramName) throws ServiceException {
        return Parameters.getNullable(req, paramName, LONG_MAPPER);
    }

    public static long getLong(HttpServletRequest req, String paramName) throws ServiceException {
        return Parameters.get(req, paramName, LONG_MAPPER);
    }

    public static Boolean getNullableBoolean(HttpServletRequest req, String paramName) throws ServiceException {
        return Parameters.getNullable(req, paramName, BOOLEAN_MAPPER);
    }

    public static boolean getBoolean(HttpServletRequest req, String paramName) throws ServiceException {
        return Parameters.get(req, paramName, BOOLEAN_MAPPER);
    }

    public static Role getRole(HttpServletRequest req) throws ServiceException {
        return Parameters.get(req, Parameter.ROLE, ROLE_MAPPER);
    }
}
