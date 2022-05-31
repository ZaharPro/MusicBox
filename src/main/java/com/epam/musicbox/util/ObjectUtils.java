package com.epam.musicbox.util;

public final class ObjectUtils {

    private ObjectUtils() {
    }

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
}
