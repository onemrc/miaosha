package com.example.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * create by: one
 * create time:2019/1/24 22:16
 * 描述：秒杀商品
 */
@Data
public class MiaoShaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
