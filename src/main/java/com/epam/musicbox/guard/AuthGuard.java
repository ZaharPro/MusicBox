package com.epam.musicbox.guard;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthGuard implements Guard {

    protected final HttpServletRequest request;

    public AuthGuard(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void protect() throws HttpException {
        HttpSession session = request.getSession();
        Object userId = session.getAttribute(Parameter.USER_ID);
        if (userId == null) {
            throw new HttpException("Unauthorized", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
