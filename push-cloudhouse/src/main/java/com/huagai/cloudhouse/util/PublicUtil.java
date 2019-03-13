package com.huagai.cloudhouse.util;

import java.util.UUID;

/**
 * @version 1.0
 * @Description:
 * @author: ChenRuiQing.
 * Create Time:  2019-03-13 下午 12:29
 */
public class PublicUtil {
//    生成UUID
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
