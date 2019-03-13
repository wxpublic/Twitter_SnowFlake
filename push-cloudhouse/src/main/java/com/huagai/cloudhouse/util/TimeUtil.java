package com.huagai.cloudhouse.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @version 1.0
 * @Description:
 * @author: ChenRuiQing.
 * Create Time:  2019-03-12 下午 12:15
 */
public class TimeUtil {
    //获取当前时间，精确到毫秒
    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time);
        return nowTimeStamp;
    }
    public static void main(String args[]) {
        System.out.println(getNowTimeStamp());
    }
}