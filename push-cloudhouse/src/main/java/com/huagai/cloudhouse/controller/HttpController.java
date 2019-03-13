package com.huagai.cloudhouse.controller;

import com.huagai.cloudhouse.httpInfo.HttpCloudHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Description:
 * @author: ChenRuiQing.
 * Create Time:  2019-03-12 上午 9:57
 */
@RestController
@RequestMapping("/cloudHouse")
public class HttpController {
    @Autowired
    private HttpCloudHouse httpCloudHouse;

    @RequestMapping("/msgPush/{userArray}/{msg}")
    public String msgPush(@PathVariable String userArray, @PathVariable String msg){
        String retStr = httpCloudHouse.cloudHouseMessagePush(userArray,msg);
        return retStr;
    }
}
