package com.choyri.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 人性化时间
 */
public class HumanTime {

    public static String get(String time) {
        String res = "";
        long currentTime = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        long targetTime;
        try {
            targetTime = sdf.parse(time).getTime();
        } catch (ParseException e) {
            targetTime = currentTime;
        }
        long tmp = Math.abs(currentTime - targetTime);

        if (tmp < 60000) {
            res = (tmp / 1000) + " 秒前";
        } else if (tmp < 3600000) {
            res = (tmp / 60000) + " 分钟前";
        } else if (tmp < 86400000){
            res = (tmp / 3600000) + " 小时前";
        } else if (tmp < 604800000){
            res = (tmp / 86400000) + " 天前";
        } else {
            sdf = new SimpleDateFormat("MM-dd HH:mm");
            res = sdf.format(targetTime).toString();
        }
        return res;
    }
}