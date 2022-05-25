package com.epam.musicbox.util;

import com.epam.musicbox.exception.HttpException;
import jakarta.servlet.http.HttpServletResponse;

public class ObjectUtils {
    public static boolean equals(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    public static int hashCode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    public static int hash(Object... objects) {
        int hash = 17;
        for (Object object : objects) {
            hash = hash * 31 + hashCode(object);
        }
        return hash;
    }

    public static int parseInt(String s) throws HttpException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new HttpException(e, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
