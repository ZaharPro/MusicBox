package com.epam.musicbox.validator.impl;

import com.epam.musicbox.validator.EntityValidator;

import java.util.regex.Pattern;

public class EntityValidatorImpl implements EntityValidator {

    private static final String LOGIN_REGEX = "^([\\w\\s:.'-]{1,30})$";
    private static final String PASSWORD_REGEX = "[A-Za-z\\d@$!%*#?&]{8,32}";
    private static final String EMAIL_REGEX =
            "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";

    private static final EntityValidatorImpl instance = new EntityValidatorImpl();

    private final Pattern loginPattern = Pattern.compile(LOGIN_REGEX);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    private EntityValidatorImpl() {
    }

    public static EntityValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean isValidLogin(String login) {
        return login != null &&
               loginPattern.matcher(login).matches();
    }

    @Override
    public boolean isValidPassword(String password) {
        return password != null &&
               passwordPattern.matcher(password).matches();
    }

    @Override
    public boolean isValidEmail(String email) {
        return email != null &&
               emailPattern.matcher(email).matches();
    }

    @Override
    public boolean isValidName(String name) {
        return name != null && name.length() > 1;
    }
}