package com.epam.musicbox.util;

import com.epam.musicbox.constant.Parameter;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.AuthService;
import com.epam.musicbox.service.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public final class Services {
    public static final int PAGE_SIZE = 20;

    private Services() {
    }

    public static int getOffset(int page) {
        return PAGE_SIZE * page;
    }

    public static String buildRegex(String name) {
        return '[' + name + "]{2,}";
    }

    public static long getUserIdFromReqOrJws(HttpServletRequest req) throws ServiceException {
        Long userId = Parameters.getNullableLong(req, Parameter.USER_ID);
        if (userId == null) {
            Jws<Claims> jws = AuthService.getInstance().getClaimsJws(req);
            Claims body = jws.getBody();
            userId = Parameters.getLong(body, Parameter.USER_ID);
        }
        return userId;
    }

    public static void savePageIndex(HttpServletRequest req, String page) throws ServiceException {
        int i = Parameters.getIntOrZero(req, page);
        req.setAttribute(page, i);
    }

    public static <T> void handlePage(HttpServletRequest req,
                                      Service<T> service,
                                      String page,
                                      String list) throws ServiceException {
        int i = Parameters.getIntOrZero(req, page);
        List<T> ts = service.findPage(i);
        req.setAttribute(page, i);
        req.setAttribute(list, ts);
    }
}
