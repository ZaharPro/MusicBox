package com.epam.musicbox.controller.command;

import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;

public class CommandResult {

    private final String page;
    private final boolean redirect;
    private final List<Cookie> cookies;

    private CommandResult(String page, boolean redirect) {
        this.page = page;
        this.redirect = redirect;
        this.cookies = new ArrayList<>();
    }

    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}