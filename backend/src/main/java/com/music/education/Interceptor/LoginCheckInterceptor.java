package com.music.education.Interceptor;

import com.alibaba.fastjson2.JSON;
import com.music.education.Entity.Result;
import com.music.education.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    // 目标方法运行前运行，true放行，false拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        // 获取请求url
        String requestURI = request.getRequestURI();
        log.info("请求的url路径为：{}", requestURI);

        // 放行登录与注册请求
        if (requestURI.contains("/login")||requestURI.contains("/register")||requestURI.contains("/images")) {
            return true;
        }

        // 获取请求头中的token
        String token = request.getHeader("me_token");
        log.info("请求头中的token为：{}", token);

        // 判断token是否为空
        if (!StringUtils.hasLength(token)) {
            log.info("token为空，请求被拦截");
            JwtUtils jwtUtils = new JwtUtils();
            Result error = Result.error("尚未登录,禁止请求");
            String not_login = JSON.toJSONString(error);
            response.getWriter().write(not_login);
            return false;
        }

        // 判断token是否有效
        try{
            JwtUtils.parseJwt(token);
        } catch (Exception e){
            log.info("token无效，请求被拦截");
            Result error = Result.error("登录失效,请重新登录");
            String not_login = JSON.toJSONString(error);
            response.getWriter().write(not_login);
            return false;
        }

        // 放行
        log.info("token有效，请求放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle ...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}

