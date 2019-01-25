package com.example.demo.util;

import java.util.UUID;

/**
 * create by: one
 * create time:2019/1/23 18:06
 * 描述：随机数工具
 */
public class UUIUtil {
    /*
  得到一个UUID
  UUID：随机生成的一个128-bit的值（不可变，唯一标识）
   */
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String getSalt(String uuid){
        return uuid.substring(0,5);
    }

    public static Long randomNum() {
        return System.currentTimeMillis();
    }
}

