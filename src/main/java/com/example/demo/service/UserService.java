package com.example.demo.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import com.example.demo.eums.StatusCode;
import com.example.demo.redis.RedisKeys;
import com.example.demo.util.ConverterUtil;
import com.example.demo.util.MD5Util;
import com.example.demo.util.UUIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * create by: one
 * create time:2019/1/23 18:28
 * 描述：用户服务
 */
@Service
public class UserService {

    private final UserDao userDao;

    private final RedisService redisService;

    public static final String USERTOKEN = "token";

    @Autowired
    public UserService(UserDao userDao, RedisService redisService) {
        this.userDao = userDao;
        this.redisService = redisService;
    }

    public String login(HttpServletResponse response, String str, String password) {
        String salt = userDao.getSaltByStr(str);
        String real_pass = MD5Util.md5(password + salt);

        //验证
        User user = userDao.queryUser(str, real_pass);

        if (user == null) {
            return ConverterUtil.getJSONString(StatusCode.ERROR);
        }

        addCookie(response, user);
        return ConverterUtil.getJSONString(StatusCode.SUCCESS);
    }

    /*
    将User存进redis，并生成一个cookie
     */
    private void addCookie(HttpServletResponse response, User user) {
        //生成一个token
        String token = UUIUtil.uuid();

        //指定一个过期时间
        int ExpireDate = 60 * 60;

        //存入redis缓存
        redisService.set(RedisKeys.getUserKey(token), ConverterUtil.beanToString(user));
        redisService.expire(RedisKeys.getUserKey(token), ExpireDate);


        Cookie cookie = new Cookie(USERTOKEN, token);

        //Cookie有效期 == redis有效期
        cookie.setMaxAge(ExpireDate);


        cookie.setPath("/");

        response.addCookie(cookie);
    }

    /*
    通过token获取用户信息
     */
    public User getByToken(HttpServletResponse response,String token){
        if (StringUtils.isEmpty(token)){
            return null;
        }


        User user = ConverterUtil.stringToBean(redisService.get(RedisKeys.getUserKey(token)),User.class);

        //延长有效期
        if (user != null){
            addCookie(response,user);
        }
        return user;
    }

    /*
    注册
     */
    public StatusCode register(User user) {
        try{
            if (userDao.isExist(user.getPhone()) != null){
                return StatusCode.PHONE_ISHAVE;
            }

            // 明文密码进行两次加密
            String salt = UUIUtil.getSalt(UUIUtil.uuid());
            String real_pass = MD5Util.md5(user.getPassword() + salt);

            user.setSalt(salt);
            user.setCreateDate(new Date());
            user.setPassword(real_pass);
            user.setName("用户："+user.getPhone());

            userDao.addUser(user);
            return StatusCode.SUCCESS;
        }catch (Exception e){
            return StatusCode.ERROR;
        }
    }
}
