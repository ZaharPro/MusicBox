package com.epam.musicbox.util;

public final class Services {
    public static final int PAGE_SIZE = 20;

    private Services() {
    }

    public static int getOffset(int page) {
        return PAGE_SIZE * page;
    }

    public static String buildRegex(String name) {
        return '[' + name + "]{2,}";
    }
}
