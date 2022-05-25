package com.epam.musicbox.guard;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.entity.Role;
import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RoleGuard extends AuthGuard {

    private final Role[] expectedRoles;

    public RoleGuard(HttpServletRequest request, Role... expectedRoles) {
        super(request);
        this.expectedRoles = expectedRoles;
    }

    @Override
    public void protect() throws HttpException {
        super.protect();
        HttpSession session = request.getSession();
        Role actualRole = (Role) session.getAttribute(Parameter.ROLE);
        for (Role expectedRole : expectedRoles) {
            if (expectedRole.equals(actualRole))
                return;
        }
        throw new HttpException("User has no rights", HttpServletResponse.SC_FORBIDDEN);
    }
}
