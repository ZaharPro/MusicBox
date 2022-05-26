package com.epam.musicbox.guard;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthGuard implements Guard {

    private final HttpServletRequest request;
    private final Role[] expectedRoles;

    public AuthGuard(HttpServletRequest request, Role... expectedRoles) {
        this.request = request;
        this.expectedRoles = expectedRoles;
    }

    @Override
    public void protect() throws HttpException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(Parameter.USER_ID);
        if (userId == null)
            throw new HttpException("Unauthorized", HttpServletResponse.SC_UNAUTHORIZED);

        if (expectedRoles.length == 0)
            return;
        Role actualRole = (Role) session.getAttribute(Parameter.ROLE);
        for (Role expectedRole : expectedRoles) {
            if (expectedRole.equals(actualRole))
                return;
        }
        throw new HttpException("User has no rights", HttpServletResponse.SC_FORBIDDEN);
    }
}
