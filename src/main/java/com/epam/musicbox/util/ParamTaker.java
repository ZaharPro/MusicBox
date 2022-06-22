package com.epam.musicbox.util;

import com.epam.musicbox.controller.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

import java.util.function.Function;

public final class ParamTaker {

    public static final String JWT_VALUE_NOT_FOUND_MSG = "Jwt value not found: ";
    public static final String REQ_INVALID_PARAMETER_MSG = "Invalid parameter: ";

    private static final Function<String, Integer> INT_MAPPER = Integer::parseInt;
    private static final Function<String, Long> LONG_MAPPER = Long::parseLong;
    private static final Function<String, Boolean> BOOLEAN_MAPPER = Boolean::parseBoolean;
    private static final Function<String, Role> ROLE_MAPPER = Role::findByName;

    private static final Integer FIRST_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 7;

    private ParamTaker() {
    }

    public static <T> T getNullable(Claims body,
                                    String paramName,
                                    Function<String, T> function) throws ServiceException {
        Object value = body.get(paramName);
        if (value == null)
            return null;
        try {
            return function.apply(value.toString());
        } catch (Throwable e) {
            throw new ServiceException(JWT_VALUE_NOT_FOUND_MSG + paramName);
        }
    }

    public static <T> T get(Claims body,
                            String paramName,
                            Function<String, T> function) throws ServiceException {
        T value = ParamTaker.getNullable(body, paramName, function);
        if (value == null)
            throw new ServiceException(JWT_VALUE_NOT_FOUND_MSG + paramName);
        return value;
    }

    public static long getLong(Claims body,
                               String paramName) throws ServiceException {
        return ParamTaker.get(body, paramName, LONG_MAPPER);
    }

    public static Role getRole(Claims body) throws ServiceException {
        return ParamTaker.get(body, Parameter.ROLE, ROLE_MAPPER);
    }

    public static <T> T getNullable(HttpServletRequest req,
                                    String paramName,
                                    Function<String, T> function) throws ServiceException {
        String value = req.getParameter(paramName);
        if (value == null)
            return null;
        try {
            return function.apply(value);
        } catch (Throwable e) {
            throw new ServiceException(REQ_INVALID_PARAMETER_MSG + paramName);
        }
    }

    public static <T> T get(HttpServletRequest req,
                            String paramName,
                            Function<String, T> function) throws ServiceException {
        T value = ParamTaker.getNullable(req, paramName, function);
        if (value == null)
            throw new ServiceException(REQ_INVALID_PARAMETER_MSG + paramName);
        return value;
    }

    public static <T> T getOrDefault(HttpServletRequest req,
                                     String paramName,
                                     Function<String, T> function,
                                     T defaultValue) throws ServiceException {
        T t = ParamTaker.getNullable(req, paramName, function);
        return t == null ? defaultValue : t;
    }

    public static Integer getNullableInt(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.getNullable(req, paramName, INT_MAPPER);
    }

    public static int getInt(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.get(req, paramName, INT_MAPPER);
    }

    public static Long getNullableLong(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.getNullable(req, paramName, LONG_MAPPER);
    }

    public static long getLong(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.get(req, paramName, LONG_MAPPER);
    }

    public static Boolean getNullableBoolean(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.getOrDefault(req, paramName, BOOLEAN_MAPPER, false);
    }

    public static boolean getBoolean(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.get(req, paramName, BOOLEAN_MAPPER);
    }

    public static Role getRole(HttpServletRequest req) throws ServiceException {
        return ParamTaker.get(req, Parameter.ROLE, ROLE_MAPPER);
    }

    public static int getPage(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.getOrDefault(req, paramName, INT_MAPPER, FIRST_PAGE);
    }

    public static int getPageSize(HttpServletRequest req, String paramName) throws ServiceException {
        return ParamTaker.getOrDefault(req, paramName, INT_MAPPER, DEFAULT_PAGE_SIZE);
    }
}
