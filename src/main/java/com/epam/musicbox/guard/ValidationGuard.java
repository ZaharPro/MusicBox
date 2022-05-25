package com.epam.musicbox.guard;

import com.epam.musicbox.exception.HttpException;
import com.epam.musicbox.validator.Validator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;

public class ValidationGuard implements Guard {
    @Inject
    private Validator validator;

    private final String login, email, password;

    public ValidationGuard(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    @Override
    public void protect() throws HttpException {
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
