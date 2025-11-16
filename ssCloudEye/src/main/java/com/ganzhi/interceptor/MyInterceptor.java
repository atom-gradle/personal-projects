package com.ganzhi.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class MyInterceptor implements HandlerInterceptor {

    //在handler方法执行之前会被调用
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURI();
        //过滤路径
        if (url.equals("/user/login") || "true".equals(request.getHeader("AiService"))){
            return true;
        }else {
            if (!StpUtil.isLogin()) {
                // 设置响应内容
                response.setContentType("application/json;charset=UTF-8");
                String json = "{\"code\":400,\"msg\":\"请登录\",\"data\":null}";
                response.getWriter().write(json);
                return false;
            }else {
                return true;
            }
        }
    }

}
