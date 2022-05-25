package com.epam.musicbox.guard;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.entity.Status;
import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class StatusGuard extends AuthGuard {
    @Inject
    private UserService userService;

    private final Status[] expectedStatuses;

    public StatusGuard(HttpServletRequest request, Status... expectedStatuses) {
        super(request);
        this.expectedStatuses = expectedStatuses;
    }


    @Override
    public void protect() throws HttpException {
        super.protect();
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(Parameter.USER_ID);
        Optional<Status> optionalStatus = userService.getStatus(userId);
        if (optionalStatus.isEmpty()) {
            throw new HttpException("User has no status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        Status actualStatus = optionalStatus.get();
        for (Status expectedStatus : expectedStatuses) {
            if (expectedStatus.equals(actualStatus))
                return;
        }
        throw new HttpException("Bad status", HttpServletResponse.SC_FORBIDDEN);
    }
}
