package com.example.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * create by: one
 * create time:2019/1/24 22:16
 * 描述：秒杀订单
 */
@Data
public class MiaoShaOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
