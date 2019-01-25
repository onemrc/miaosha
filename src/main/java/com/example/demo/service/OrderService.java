package com.example.demo.service;

import com.example.demo.dao.OrderDao;
import com.example.demo.domain.MiaoShaOrder;
import com.example.demo.domain.OrderInfo;
import com.example.demo.domain.User;
import com.example.demo.eums.StatusCode;
import com.example.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * create by: one
 * create time:2019/1/25 15:04
 * 描述：订单服务
 */
@Service
public class OrderService {
    private final OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {
//        保存订单信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsId(goodsVo.getGoodsId());
        orderInfo.setAddress("xx");
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setStatus(StatusCode.ORDER_STATUS_CREATE.getCode());
        orderInfo.setUserId(user.getUserId());

        long orderId = orderDao.insert(orderInfo);

        //保存秒杀订单信息
        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setGoodsId(goodsVo.getGoodsId());
        miaoShaOrder.setOrderId(orderId);
        miaoShaOrder.setUserId(user.getUserId());
        orderDao.insertMiaoshaOrder(miaoShaOrder);

        return orderInfo;
    }

    /*
    取秒杀订单信息
     */
    public MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }
}
