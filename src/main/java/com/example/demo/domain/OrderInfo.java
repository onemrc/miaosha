package com.example.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * create by: one
 * create time:2019/1/24 22:20
 * 描述：订单
 */
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private String  address;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
//    private Integer orderChannel;//下单渠道 0PC、1手机
    private Integer status;
    private Date createDate;
    private Date payDate;
}
