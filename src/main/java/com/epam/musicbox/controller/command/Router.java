package com.epam.musicbox.controller.command;

import jakarta.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Router.
 */
public class Router {

    private final String page;
    private final boolean redirect;
    private final List<Cookie> cookies;
    private boolean cache;

    private Router(String page, boolean redirect) {
        this.page = page;
        this.redirect = redirect;
        this.cookies = new ArrayList<>();
    }

    /**
     * Create new forward router.
     *
     * @param page the page
     * @return the router
     */
    public static Router forward(String page) {
        return new Router(page, false);
    }

    /**
     * Create new redirect router.
     *
     * @param page the page
     * @return the router
     */
    public static Router redirect(String page) {
        return new Router(page, true);
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Is redirect boolean.
     *
     * @return the boolean
     */
    public boolean isRedirect() {
        return redirect;
    }

    /**
     * Gets cookies.
     *
     * @return the cookies
     */
    public List<Cookie> getCookies() {
        return cookies;
    }

    /**
     * Is cache boolean.
     *
     * @return the boolean
     */
    public boolean isCache() {
        return cache;
    }

    /**
     * Sets cache.
     *
     * @param cache the cache
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }
}