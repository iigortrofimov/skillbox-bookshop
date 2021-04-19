package com.bookshop.mybookshop.util;

public class CookieUtils {

    public static String[] createArrayWithBookSlugsFromCookie(String stringFromCookie) {
        stringFromCookie = stringFromCookie.startsWith("/") ? stringFromCookie.substring(1) : stringFromCookie;
        stringFromCookie = stringFromCookie.endsWith("/") ? stringFromCookie.substring(0, stringFromCookie.length() - 1)
                : stringFromCookie;
        return stringFromCookie.split("/");
    }
}
