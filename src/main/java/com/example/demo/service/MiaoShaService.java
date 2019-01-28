package com.example.demo.service;

import com.example.demo.dao.GoodsDao;
import com.example.demo.dao.OrderDao;
import com.example.demo.domain.MiaoShaOrder;
import com.example.demo.domain.OrderInfo;
import com.example.demo.domain.User;
import com.example.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by: one
 * create time:2019/1/26 21:00
 * 描述：秒杀订单服务
 */
@Service
public class MiaoShaService {

    private final GoodsService goodsService;

    private final OrderService orderService;

    public MiaoShaService(GoodsService goodsService, OrderService orderService) {
        this.goodsService = goodsService;
        this.orderService = orderService;
    }


    /*
    秒杀
     */
    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo) {
        //减库存
        boolean reduceResult = goodsService.reduceStock(goodsVo);
        if (reduceResult){//下订单
            orderService.createOrder(user, goodsVo);
        }
        return null;
    }

    /**
     * 获取秒杀结果
     */
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoShaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }
//        else {
//            boolean isOver = getGoodsOver(goodsId);
//            if(isOver) {
//                return -1;
//            }else {
//                return 0;
//            }
//        }
        return -1;
    }
}
