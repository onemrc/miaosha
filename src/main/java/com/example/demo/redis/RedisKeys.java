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

    private static final String GOODSLIST = "GOODSLIST";

    private static final String GOODS_DETAIL = "GOODS_DETAIL";

    public static final int  CACHE_EXPIRE_DATE = 60 * 60 ;

    /**
     * 记录用户状态的token
     * @param token
     * @return
     */
    public static String getUserKey(String token){
        return BASE+SPLIT+USERTOKEN+SPLIT+token;
    }

    /**
     * 记录商品列表缓存信息
     * @return
     */
    public static String getGoodslist(){return GOODSLIST;}

    /*
    记录商品详情信息
     */
    public static String getGoodsDetail(long goodsId){
        return GOODS_DETAIL+"_"+goodsId;
    }
}
