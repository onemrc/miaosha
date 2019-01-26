package com.example.demo.service;

import com.example.demo.dao.GoodsDao;
import com.example.demo.domain.MiaoShaGoods;
import com.example.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by: one
 * create time:2019/1/24 23:03
 * 描述：
 */
@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.GoodsVoList();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /*
    减库存
     */
    public void reduceStock(GoodsVo goods) {
        MiaoShaGoods g = new MiaoShaGoods();
        g.setGoodsId(goods.getGoodsId());
        goodsDao.reduceStock(g);
    }
}
