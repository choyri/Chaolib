package com.choyri.util;

import java.util.ResourceBundle;

/**
 * 获取配置
 */
public class Config {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("config");
    }

    public static String get(String key) {
        return bundle.getString(key);
    }
}