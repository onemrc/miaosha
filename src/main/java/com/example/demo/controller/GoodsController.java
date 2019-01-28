package com.example.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.domain.User;
import com.example.demo.redis.RedisKeys;
import com.example.demo.service.GoodsService;
import com.example.demo.service.RedisService;
import com.example.demo.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * create by: one
 * create time:2019/1/23 18:27
 * 描述：商品入口
 */
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    private final GoodsService goodsService;

    private final RedisService redisService;

    private final ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    public GoodsController(GoodsService goodsService, RedisService redisService, ThymeleafViewResolver thymeleafViewResolver) {
        this.goodsService = goodsService;
        this.redisService = redisService;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    /**
     * 并发量 1000
     * QPS : 188
     */
//    @RequestMapping(value="/to_list")
//    public String list(Model model, User user) {
//        model.addAttribute("user", user);
//        List<GoodsVo> goodsList = goodsService.listGoodsVo();
//        model.addAttribute("goodsList", goodsList);
//        return "goods_list";
//    }

    /**
     * 进行页面缓存处理
     * 并发量 1000
     * QPS ：254.8
     */
    @RequestMapping(value="/to_list",produces = "text/html")
    @ResponseBody
    public String list(Model model, User user,
                       HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(RedisKeys.getGoodslist());
        if (!StringUtils.isEmpty(html)){
//            logger.info("取goods_list 缓存");
            return html;
        }

        //没有缓存就手动渲染
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);


        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",webContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(RedisKeys.getGoodslist(),html,RedisKeys.CACHE_EXPIRE_DATE);
        }
//        logger.info("手动渲染goods_list");
        return html;
    }



    @RequestMapping("/to_detail/{goodsId}")
    @ResponseBody
    public String detail(Model model,User user,
                         @PathVariable("goodsId")long goodsId,
                         HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(RedisKeys.getGoodsDetail(goodsId));
        if (!StringUtils.isEmpty(html)){
            return html;
        }

        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;//秒杀状态
        int remainSeconds = 0;//秒杀倒计时, s
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
//        return "goods_detail";

        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",webContext);
        if (!StringUtils.isEmpty(html)){
            redisService.set(RedisKeys.getGoodsDetail(goodsId),html,RedisKeys.CACHE_EXPIRE_DATE);
        }
        return html;
    }
}
