package com.epam.musicbox.util;

import java.util.Arrays;

public final class Services {
    public static final int PAGE_SIZE = 20;

    private Services() {
    }

    public static int getOffset(int page) {
        return PAGE_SIZE * page;
    }

    public static String buildRegex(String name) {
        char[] chars = name.toCharArray();
        Arrays.sort(chars);
        int dest = 0;
        int src = 1;
        while (src < chars.length) {
            if (chars[src] != chars[dest]) {
                dest++;
                chars[dest] = chars[src];
            }
            src++;
        }
        int size = dest + 1;
        int charCount = size >> 1;
        String charSet = new String(chars, 0, size);
        return new StringBuilder()
                .append('[').append(charSet).append(']')
                .append('{').append(charCount).append('}')
                .toString();
    }
}
