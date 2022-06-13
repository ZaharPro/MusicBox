package com.epam.musicbox.util;

import com.epam.musicbox.entity.Entity;
import com.epam.musicbox.exception.ServiceException;
import com.epam.musicbox.service.EntityService;
import com.epam.musicbox.service.impl.AuthServiceImpl;
import com.epam.musicbox.util.constant.Parameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class Commands {
    public static long getUserIdFromReqOrJws(HttpServletRequest req) throws ServiceException {
        Long userId = Parameters.getNullableLong(req, Parameter.USER_ID);
        if (userId == null) {
            Jws<Claims> jws = AuthServiceImpl.getInstance().getJws(req);
            Claims body = jws.getBody();
            userId = Parameters.getLong(body, Parameter.USER_ID);
        }
        return userId;
    }

    public static void savePageIndex(HttpServletRequest req, String page) throws ServiceException {
        int i = Parameters.getIntOrZero(req, page);
        req.setAttribute(page, i);
    }

    public static <T extends Entity<K>, K> void handlePage(HttpServletRequest req,
                                                           EntityService<T, K> service,
                                                           String page,
                                                           String list) throws ServiceException {
        int i = Parameters.getIntOrZero(req, page);
        List<T> ts = service.findPage(i);
        req.setAttribute(page, i);
        req.setAttribute(list, ts);
    }
}
