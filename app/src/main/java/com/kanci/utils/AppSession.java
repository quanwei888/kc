package com.kanci.utils;

/**
 * Created by qw on 18-12-8.
 */

public class AppSession {
    public static int uid = 0;
    public static String cookie;
    public static String userName;
    public static String pic;

    public static boolean isLogin() {
        return uid > 0;
    }
}
