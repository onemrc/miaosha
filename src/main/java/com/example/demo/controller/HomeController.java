package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.eums.StatusCode;
import com.example.demo.service.UserService;
import com.example.demo.util.ConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * create by: one
 * create time:2019/1/23 17:58
 * 描述：一些页面的跳转和登陆入口
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/to_login")
    public String toLogin(){return "login";}

    @GetMapping(value = "/to_register")
    public String toRegister(){return "registered";}

    @PostMapping(value = "/registered")
    @ResponseBody
    public String registered(@RequestParam("mobile") Long mobile,
                             @RequestParam("password") String password){
        User user = new User();
        user.setPassword(password);
        user.setPhone(mobile);

        return ConverterUtil.getJSONString(userService.register(user));
    }

    @PostMapping(value = "/do_login")
    @ResponseBody
    public String doLogin(@RequestParam("mobile") Long mobile,
                          @RequestParam("password") String password,
                          HttpServletResponse response){
        return userService.login(response,mobile+"",password);
    }
}
