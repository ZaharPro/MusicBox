package com.epam.musicbox.controller;

import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.ServiceException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

/**
 * The type Parameter taker.
 */
public final class ParameterTaker {

    private static final Logger logger = LogManager.getLogger();

    private static final String JWT_VALUE_NOT_FOUND_MSG = "Jwt value not found: ";
    private static final String JWT_VALUE_ERROR_MSG = "Jwt value not found: ";
    private static final String REQ_INVALID_PARAMETER_MSG = "Invalid parameter: ";
    private static final String REQ_PARAMETER_ERROR_MSG = "Invalid parameter: ";

    private static final Function<String, Integer> INT_MAPPER = Integer::parseInt;
    private static final Function<String, Long> LONG_MAPPER = Long::parseLong;
    private static final Function<String, Boolean> BOOLEAN_MAPPER = Boolean::parseBoolean;
    private static final Function<String, Role> ROLE_MAPPER = Role::findByName;

    private static final int FIRST_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 7;

    private ParameterTaker() {
    }

    /**
     * Gets optional.
     *
     * @param <T>       the type parameter
     * @param body      the body
     * @param paramName the param name
     * @param function  the function
     * @return the optional
     */
    public static <T> Optional<T> getOptional(Claims body, String paramName, Function<String, T> function) {
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

    /**
     * Gets long.
     *
     * @param body      the body
     * @param paramName the param name
     * @return the long
     * @throws ServiceException the service exception
     */
    public static long getLong(Claims body, String paramName) throws ServiceException {
        return ParameterTaker.getOptional(body, paramName, LONG_MAPPER).orElseThrow(() -> {
            return new ServiceException(JWT_VALUE_NOT_FOUND_MSG + paramName);
        });
    }

    /**
     * Gets role.
     *
     * @param body the body
     * @return the role
     * @throws ServiceException the service exception
     */
    public static Role getRole(Claims body) throws ServiceException {
        return ParameterTaker.getOptional(body, Parameter.ROLE, ROLE_MAPPER).orElseThrow(() -> {
            return new ServiceException(JWT_VALUE_NOT_FOUND_MSG + Parameter.ROLE);
        });
    }

    /**
     * Gets optional.
     *
     * @param <T>       the type parameter
     * @param req       the http request
     * @param paramName the param name
     * @param function  the function
     * @return the optional
     */
    public static <T> Optional<T> getOptional(HttpServletRequest req, String paramName, Function<String, T> function) {
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

    /**
     * Gets optional long.
     *
     * @param req       the http request
     * @param paramName the param name
     * @return the optional long
     */
    public static Optional<Long> getOptionalLong(HttpServletRequest req, String paramName) {
        return ParameterTaker.getOptional(req, paramName, LONG_MAPPER);
    }

    /**
     * Gets long.
     *
     * @param req       the http request
     * @param paramName the param name
     * @return the long
     * @throws ServiceException the service exception
     */
    public static long getLong(HttpServletRequest req, String paramName) throws ServiceException {
        return ParameterTaker.getOptional(req, paramName, LONG_MAPPER).orElseThrow(() -> {
            return new ServiceException(REQ_INVALID_PARAMETER_MSG + paramName);
        });
    }

    /**
     * Gets boolean.
     *
     * @param req       the http request
     * @param paramName the param name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    public static boolean getBoolean(HttpServletRequest req, String paramName) throws ServiceException {
        return ParameterTaker.getOptional(req, paramName, BOOLEAN_MAPPER).orElseThrow(() -> {
            return new ServiceException(REQ_INVALID_PARAMETER_MSG + paramName);
        });
    }

    /**
     * Gets role.
     *
     * @param req the http request
     * @return the role
     * @throws ServiceException the service exception
     */
    public static Role getRole(HttpServletRequest req) throws ServiceException {
        return ParameterTaker.getOptional(req, Parameter.ROLE, ROLE_MAPPER).orElseThrow(() -> {
            return new ServiceException(REQ_INVALID_PARAMETER_MSG + Parameter.ROLE);
        });
    }

    /**
     * Gets page.
     *
     * @param req       the http request
     * @param paramName the param name
     * @return the page
     */
    public static int getPage(HttpServletRequest req, String paramName) {
        return ParameterTaker.getOptional(req, paramName, INT_MAPPER).orElse(FIRST_PAGE);
    }

    /**
     * Gets page size.
     *
     * @param req       the http request
     * @param paramName the param name
     * @return the page size
     */
    public static int getPageSize(HttpServletRequest req, String paramName) {
        return ParameterTaker.getOptional(req, paramName, INT_MAPPER).orElse(DEFAULT_PAGE_SIZE);
    }

    /**
     * Gets part.
     *
     * @param req  the req
     * @param name the name
     * @return the part
     * @throws ServiceException the service exception
     */
    public static Part getPart(HttpServletRequest req, String name) throws ServiceException {
        try {
            return req.getPart(name);
        } catch (IOException | ServletException e) {
            throw new ServiceException(e);
        }
    }
}
