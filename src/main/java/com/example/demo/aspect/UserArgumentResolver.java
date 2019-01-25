package com.example.demo.aspect;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create by: one
 * create time:2019/1/25 14:06
 * 描述：解析User参数
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Autowired
    public UserArgumentResolver(UserService userService) {
        this.userService = userService;
    }

    /*
    检查参数是否一致
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    /*
    从给定请求将方法参数解析为参数值。
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(userService.USERTOKEN);//从请求参数获取token
        String cookieToken = getCookieValue(request,userService.USERTOKEN);//从cookie获取token

        if (paramToken == null && cookieToken == null){
            return null;
        }

        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;

        return userService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
