package com.epam.musicbox.controller;

import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.util.validator.Validator;
import com.epam.musicbox.util.validator.impl.ValidatorImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Function;

public final class ParameterTaker {

    private static final Logger logger = LogManager.getLogger();

    public static final String JWT_VALUE_NOT_FOUND_MSG = "Jwt value not found: ";
    public static final String JWT_VALUE_ERROR_MSG = "Jwt value not found: ";
    public static final String REQ_INVALID_PARAMETER_MSG = "Invalid parameter: ";
    public static final String REQ_PARAMETER_ERROR_MSG = "Invalid parameter: ";

    private static final Function<String, Integer> INT_MAPPER = Integer::parseInt;
    private static final Function<String, Long> LONG_MAPPER = Long::parseLong;
    private static final Function<String, Boolean> BOOLEAN_MAPPER = Boolean::parseBoolean;
    private static final Function<String, Role> ROLE_MAPPER = Role::findByName;

    private static final int FIRST_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 7;

    private ParameterTaker() {
    }

    public static <T> Optional<T> getOptional(Claims body,
                                              String paramName,
                                              Function<String, T> function) {
        Object value = body.get(paramName);
        if (value == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(function.apply(value.toString()));
        } catch (NumberFormatException e) {
            logger.info(JWT_VALUE_ERROR_MSG + paramName, e);
            return Optional.empty();
        }
    }

    public static long getLong(Claims body,
                               String paramName) throws ServiceException {
        return ParameterTaker.getOptional(body, paramName, LONG_MAPPER).orElseThrow(() -> {
            return new ServiceException(JWT_VALUE_NOT_FOUND_MSG + paramName);
        });
    }

    public static Role getRole(Claims body) throws ServiceException {
        return ParameterTaker.getOptional(body, Parameter.ROLE, ROLE_MAPPER).orElseThrow(() -> {
            return new ServiceException(JWT_VALUE_NOT_FOUND_MSG + Parameter.ROLE);
        });
    }

    public static <T> Optional<T> getOptional(HttpServletRequest req,
                                              String paramName,
                                              Function<String, T> function) {
        String value = req.getParameter(paramName);
        if (value == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(function.apply(value));
        } catch (NumberFormatException e) {
            logger.info(REQ_PARAMETER_ERROR_MSG + paramName, e);
            return Optional.empty();
        }
    }

    public static Optional<Long> getOptionalLong(HttpServletRequest req, String paramName) {
        return ParameterTaker.getOptional(req, paramName, LONG_MAPPER);
    }

    public static long getLong(HttpServletRequest req, String paramName) throws ServiceException {
        return ParameterTaker.getOptional(req, paramName, LONG_MAPPER).orElseThrow(() -> {
            return new ServiceException(REQ_INVALID_PARAMETER_MSG + paramName);
        });
    }

    public static boolean getBoolean(HttpServletRequest req, String paramName) throws ServiceException {
        return ParameterTaker.getOptional(req, paramName, BOOLEAN_MAPPER).orElseThrow(() -> {
            return new ServiceException(REQ_INVALID_PARAMETER_MSG + paramName);
        });
    }

    public static Role getRole(HttpServletRequest req) throws ServiceException {
        return ParameterTaker.getOptional(req, Parameter.ROLE, ROLE_MAPPER).orElseThrow(() -> {
            return new ServiceException(REQ_INVALID_PARAMETER_MSG + Parameter.ROLE);
        });
    }

    public static int getPage(HttpServletRequest req, String paramName) {
        return ParameterTaker.getOptional(req, paramName, INT_MAPPER).orElse(FIRST_PAGE);
    }

    public static int getPageSize(HttpServletRequest req, String paramName) {
        return ParameterTaker.getOptional(req, paramName, INT_MAPPER).orElse(DEFAULT_PAGE_SIZE);
    }

    public static Optional<String> getName(HttpServletRequest req) {
        String name = req.getParameter(Parameter.NAME);
        Validator validator = ValidatorImpl.getInstance();
        if (validator.isValidName(name)) {
            return Optional.of(name);
        }
        return Optional.empty();
    }
}
