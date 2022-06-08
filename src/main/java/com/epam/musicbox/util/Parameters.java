package com.epam.musicbox.util;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
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
        Object value = body.get(paramName);
        if (value == null)
            return null;
        try {
            return function.apply(value.toString());
        } catch (Throwable e) {
            return null;
        }
    }

    public static <T> T get(Claims body,
                            String paramName,
                            Function<String, T> function) throws ServiceException {
        T value = Parameters.getNullable(body, paramName, function);
        if (value == null)
            throw new ServiceException(String.format("Jwt token value: '%s', not found", paramName));
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
        String value = req.getParameter(paramName);
        if (value == null || value.isBlank())
            return null;
        try {
            return function.apply(value);
        } catch (Throwable e) {
            throw new ServiceException(String.format("Invalid parameter: '%s' = '%s'", paramName, value));
        }
    }

    public static <T> T get(HttpServletRequest req,
                            String paramName,
                            Function<String, T> function) throws ServiceException {
        T value = Parameters.getNullable(req, paramName, function);
        if (value == null)
            throw new ServiceException("Parameter not found: " + paramName);
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

    public static int getIntOrZero(HttpServletRequest req, String paramName) throws ServiceException {
        Integer nullableInt = Parameters.getNullableInt(req, paramName);
        return nullableInt == null ? 0 : nullableInt;
    }
}
