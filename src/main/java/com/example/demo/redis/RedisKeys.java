package com.example.demo.redis;

/**
 * create by: one
 * create time:2019/1/23 18:45
 * 描述：redis的各种key
 */
public class RedisKeys {
    private static final String BASE = "MIAOSHA";

    private static final String SPLIT = ":";

    private static final String USERTOKEN = "USERTOKEN";

    /**
     * 记录用户状态的token
     * @param token
     * @return
     */
    public static String getUserKey(String token){
        return BASE+SPLIT+USERTOKEN+SPLIT+token;
    }
}
