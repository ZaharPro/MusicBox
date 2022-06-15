package com.epam.musicbox.util;

public final class ServiceUtils {

    private ServiceUtils() {
    }

    public static String buildRegex(String name) {
        return '[' + name + "]{2,}";
    }

    public static int getOffset(int page, int pageSize) {
        return (page - 1) * pageSize;
    }
}
