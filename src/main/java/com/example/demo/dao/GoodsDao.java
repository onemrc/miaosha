package com.example.demo.dao;

import com.example.demo.domain.MiaoShaGoods;
import com.example.demo.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * create by: one
 * create time:2019/1/24 22:23
 * 描述：商品相关DAO操作
 */
@Mapper
public interface GoodsDao {

    @Select({"select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price  from miaosha_goods mg left join goods g on mg.goods_id = g.goods_id"})
    public List<GoodsVo> GoodsVoList();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.goods_id where g.goods_id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    public int reduceStock(MiaoShaGoods mg);
}
