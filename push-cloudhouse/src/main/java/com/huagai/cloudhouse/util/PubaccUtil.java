package com.huagai.cloudhouse.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.Arrays;

public class PubaccUtil {

    public static JSONObject genernateFrom(String no, String pubId, String pubsercet, String nonce, String time) {
        JSONObject jsonFrom = new JSONObject();
        jsonFrom.put("no", no);
        jsonFrom.put("pub", pubId);
        jsonFrom.put("nonce", nonce);
        jsonFrom.put("time", time);
        String pubtoken = sha(no, pubId, pubsercet, nonce, time);
        jsonFrom.put("pubtoken", pubtoken);
        return jsonFrom;
    }

    private static String sha(String... data) {
        Arrays.sort(data);
        String join = StringUtils.join(data);
        String pubtoken = DigestUtils.sha1Hex(join);
        return pubtoken;
    }
}