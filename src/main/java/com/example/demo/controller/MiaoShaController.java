package com.example.demo.controller;

import com.example.demo.domain.MiaoShaOrder;
import com.example.demo.domain.OrderInfo;
import com.example.demo.domain.User;
import com.example.demo.eums.StatusCode;
import com.example.demo.service.GoodsService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RedisService;
import com.example.demo.service.UserService;
import com.example.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * create by: one
 * create time:2019/1/25 14:39
 * 描述：秒杀入口
 */
@Controller
public class MiaoShaController {
    private final UserService userService;
    private final RedisService redisService;
    private final GoodsService goodsService;

    private final OrderService orderService;

    @Autowired
    public MiaoShaController(UserService userService, RedisService redisService, GoodsService goodsService, OrderService orderService) {
        this.userService = userService;
        this.redisService = redisService;
        this.goodsService = goodsService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/do_miaosha")
    public String doMiaoSha(User user,
                            Model model,
                            @RequestParam("goodsId") long goodsId){
        model.addAttribute("user",user);
        if (user == null){
            return "to_login";
        }

        //秒杀商品信息
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();

        if (stock == 0){
            model.addAttribute("msg", StatusCode.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        //进行秒杀

        //不能重复购买，先判断这个人是否已经秒杀成功
        MiaoShaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getUserId(),goodsVo.getGoodsId());
        if (order != null){
            model.addAttribute("msg", StatusCode.MIAO_ORDER_EXIST.getMsg());
            return "miaosha_fail";
        }

        //减库存，下订单 写入秒杀订单
        OrderInfo orderInfo = orderService.createOrder(user,goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);

        return "order_detail";
    }
}
