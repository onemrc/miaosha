package com.example.demo.domain;

import lombok.Data;

import java.util.Date;


/**
 * create by: one
 * create time:2019/1/23 18:17
 * 描述：用户信息
 */
@Data
public class User {
    //用户id
    private Integer userId;

    //用户名
    private String name;

    //用户密码
    private String password;

    //加密密码用
    private String salt;

    //用户头像地址
    private String headUrl;


    //用户性别
    private Integer sex;


    //手机号
    private Long phone;

    //邮箱
    private String email;

    //注册时间
    private Date createDate;
}
