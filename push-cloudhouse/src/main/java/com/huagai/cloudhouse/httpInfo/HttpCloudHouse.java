package com.huagai.cloudhouse.httpInfo;

import com.huagai.cloudhouse.util.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @version 1.0
 * @Description:
 * @author: ChenRuiQing.
 * Create Time:  2019-03-12 上午 10:28
 */
@Service
public class HttpCloudHouse {
    // 获取token返回
    public String getAccessToken(){
        System.out.println("createAccessToken_token Method");
        String url = "https://www.yunzhijia.com/gateway/oauth2/token/getAccessToken";
        String jsonStr = "{\"eid\": \"1610800\",\"secret\": \"zYbVGi9yMdsFk98pZqiykxEQHOE0eU6X\",\"timestamp\": "+TimeUtil.getNowTimeStamp()+",\"scope\": \"resGroupSecret\"}";
        JSONObject jsonParam = new JSONObject(jsonStr);
        String retAll = HttpUtil.postJSON(url,jsonParam);
        String retStr = getToken(retAll);
        return retStr;
    }

    //获取最终token
    private String getToken(String jsonStr){
        if(null!=jsonStr) {
            JSONObject sjson  = (JSONObject) new JSONObject(jsonStr).get("data");
            return sjson.getString("accessToken");
        }else{
            return "";
        }
    }

    //刷新token
    public String refreshAccessToken(){
        System.out.println("refreshAccessToken_token Method");
        String url = "https://www.yunzhijia.com/gateway/oauth2/token/refreshToken";
        String jsonStr = "{\"eid\": \"1610800\",\"refreshToken\": \"TRTWql4jIUawkkIvIvzT9vTxvqYWi7j1\",\"timestamp\": "+TimeUtil.getNowTimeStamp()+",\"scope\": \"resGroupSecret\"}";
        JSONObject jsonParam = new JSONObject(jsonStr);
        String retAll = HttpUtil.postJSON(url,jsonParam);
        String retStr = getToken(retAll);
        return retAll;
    }

    //创建群聊
    public String createGroupChat(){
        String token = getAccessToken();
        System.out.println("createGroupChat_token:  "+token);
        String url = "https://www.yunzhijia.com/gateway/xtinterface/group/createGroup?accessToken="+token;
        String jsonStr = "{\"groupName\": \"讨论组_11111\",\"currentUid\":\"5b8369dae4b0f5e3d46d57ba\",\"userIds\":[\"5b8369dae4b0f5e3d46d57ba\",\"5c6bcdf7e4b0eb1afc032d06\",\"5c6fb501e4b0373dbe533458\"]}";
        JSONObject jsonParam = new JSONObject(jsonStr);
        String retStr = HttpUtil.postJSON(url,jsonParam);
        return retStr;
    }

    //推送群聊公告
    public String pushGroupNotice(){
        String token = getAccessToken();
        System.out.println("pushGroupNotice_token:  "+token);
        String url = "https://www.yunzhijia.com/gateway/xtinterface/notice/create?accessToken="+token;
        String jsonStr = "{\"currentUid\": \"5b8369dae4b0f5e3d46d57ba\",\"groupId\": \"5c887071e4b065155260d78d\",\"title\": \"消息推送接口测试\",\"content\": \"消息推送接口测试，信息发送成功！2222222222222222222\"}";
        JSONObject jsonParam = new JSONObject(jsonStr);
        String retStr = HttpUtil.postJSON(url,jsonParam);
        return retStr;
    }

    /**
     * @description 云之家公众号消息推送
     * @param userArrayParam 用户ID数组，逗号分隔,双引号括起来
     * @param msg 消息内容
     * @return
     */
    public String cloudHouseMessagePush(String userArrayParam, String msg){
        String [] userArray = userArrayParam.split(",");
        System.out.println("StaticUtil.nonce"+PublicUtil.getUUID32());
        System.out.println("StaticUtil.time"+TimeUtil.getNowTimeStamp());
        JSONObject fromJson = PubaccUtil.genernateFrom(StaticUtil.no,StaticUtil.pubId,StaticUtil.pubSecret,PublicUtil.getUUID32(), TimeUtil.getNowTimeStamp());
        JSONObject innerToJson = new JSONObject();
        innerToJson.put("no",StaticUtil.no);
        innerToJson.put("user", Arrays.toString(userArray));
        System.out.println("innerToJson: "+innerToJson.toString());

        String[] innerToArray = {innerToJson.toString()};
        System.out.println("innerToArray: "+Arrays.toString(innerToArray));

        JSONObject msgJson = new JSONObject();
        msgJson.put("text",msg);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("from",fromJson);
        jsonParam.put("to",Arrays.toString(innerToArray));
        jsonParam.put("type",2);
        jsonParam.put("msg",msgJson);
        String strJson = jsonParam.toString().replace("\"[","[").replace("]\"","]").replace("\\","").replace("]\"","]");
        JSONObject lastJson = new JSONObject(strJson);
        String retStr = HttpUtil.postJSON(StaticUtil.msgPushUrl,lastJson);
        return retStr;
    }

    public static void main(String[] args) {
        String userArray = "5b8369dae4b0f5e3d46d57ba";
        String msg = "66622222222222222222222226";
        System.out.println(new HttpCloudHouse().cloudHouseMessagePush(userArray,msg));
    }
}
