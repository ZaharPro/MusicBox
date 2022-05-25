package com.epam.musicbox.exception;

import jakarta.servlet.http.HttpServletResponse;

public class HttpException extends Exception {
    public static final int DEFAULT_STATUS_CODE = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    private final int statusCode;

    public HttpException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public HttpException(Throwable cause) {
        this(cause, DEFAULT_STATUS_CODE);
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ", statusCode = " + statusCode;
    }
}