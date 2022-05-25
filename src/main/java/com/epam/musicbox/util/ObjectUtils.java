package com.epam.musicbox.util;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.validator.Validator;
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

    public static void requireValid(Validator validator,
                                    String login,
                                    String email,
                                    String password) throws HttpException {
        String msg = null;
        if (!validator.isValidLogin(login)) {
            msg = "Invalid login";
        }
        if (!validator.isValidEmail(email)) {
            if (msg == null) {
                msg = "Invalid email";
            } else {
                msg = msg + ", invalid email";
            }
        }
        if (!validator.isValidPassword(password)) {
            if (msg == null) {
                msg = "Invalid password";
            } else {
                msg = msg + ", invalid password";
            }
        }
        if (msg != null)
            throw new HttpException(msg, HttpServletResponse.SC_BAD_REQUEST);
    }
}
