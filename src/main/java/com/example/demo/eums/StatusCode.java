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
    NO_LOGIN("未登录",999),

//    秒杀状态
    MIAO_SHA_OVER("秒杀已结束",5001),
    MIAO_ORDER_EXIST("秒杀订单已存在，不能重复购买",5002),


//    订单状态
    ORDER_STATUS_CREATE("新建未支付",3000),
    ORDER_STATUS_PAY("已支付",3001),
    ORDER_STATUS_SHIPPED("已发货",3002),
    ORDER_STATUS_REFUNDED("已退款",3003),
    ORDER_STATUS_RECEIVED("已收货",3003),
    ORDER_STATUS_COMPLETED("已完成",3003),

    ;





    private String msg;
    private Integer code;

    StatusCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
