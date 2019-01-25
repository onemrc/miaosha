package com.example.demo.eums;

import lombok.Getter;

/**
 * create by: one
 * create time:2019/1/24 14:36
 * 描述：一些状态码
 */
@Getter
public enum  StatusCode {
    SUCCESS("成功",200),
    ERROR("失败",500),
    PHONE_ISHAVE("手机号已被注册",502),

    MIAO_SHA_OVER("秒杀已结束",50001);



    private String msg;
    private Integer code;

    StatusCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
