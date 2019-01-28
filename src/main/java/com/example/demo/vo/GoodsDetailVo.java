package com.example.demo.vo;

import com.example.demo.domain.User;
import lombok.Data;

/**
 * create by: one
 * create time:2019/1/28 17:33
 * 描述：
 */
@Data
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods ;
    private User user;
}
