package com.epam.musicbox.util;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.validator.Validator;
import jakarta.servlet.http.HttpServletResponse;

public class AuthUtils {
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
