package com.example.demo.domain;

import lombok.Data;

/**
 * create by: one
 * create time:2019/1/24 17:19
 * 描述：商品信息
 */
@Data
public class Goods {
    private Long GoodsId;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
