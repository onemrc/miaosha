package com.example.demo.vo;

import com.example.demo.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * create by: one
 * create time:2019/1/24 22:26
 * 描述：
 */
@Data
public class GoodsVo extends Goods {
    private Integer id;
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
