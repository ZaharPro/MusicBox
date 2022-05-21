package com.epam.musicbox.exception;

public class HttpException extends Exception {
    private final int statusCode;

    public HttpException(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public HttpException(int statusCode, Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
