package com.epam.musicbox.util;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.validator.Validator;
import jakarta.servlet.http.HttpServletResponse;

public class AuthUtils {
    public static void requireValid(Validator validator,
                                    String login,
                                    String email,
                                    String password) throws HttpException {
        StringBuilder sb = new StringBuilder();
        if (!validator.isValidLogin(login)) {
            sb.append("Invalid login");
        }
        if (!validator.isValidEmail(email)) {
            String s = sb.isEmpty() ?
                    "Invalid email" :
                    ", invalid email";
            sb.append(s);
        }
        if (!validator.isValidPassword(password)) {
            String s = sb.isEmpty() ?
                    "Invalid password" :
                    ", invalid password";
            sb.append(s);
        }
        if (!sb.isEmpty()) {
            String msg = sb.toString();
            throw new HttpException(msg, HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
