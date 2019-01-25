package com.example.demo.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * create by: one
 * create time:2019/1/23 18:24
 * 描述：MD5加密工具
 */
public class MD5Util {
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }
}
