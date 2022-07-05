package com.epam.musicbox.controller.command;

import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;

public class Router {

    private final String page;
    private final boolean redirect;
    private final List<Cookie> cookies;

    private Router(String page, boolean redirect) {
        this.page = page;
        this.redirect = redirect;
        this.cookies = new ArrayList<>();
    }

    public static Router forward(String page) {
        return new Router(page, false);
    }

    public static Router redirect(String page) {
        return new Router(page, true);
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