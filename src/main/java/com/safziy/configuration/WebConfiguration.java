package com.safziy.configuration;

import com.safziy.controller.base.LogInterceptor;
import com.safziy.controller.base.LoginInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    LogInterceptor logInterceptor;
    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).order(0);
        registry.addInterceptor(loginInterceptor).order(1)
                .addPathPatterns("/**")  //设置拦截器拦截的请求路径 /* 表示拦截一级路径 /**表示拦截任意级路径
                .excludePathPatterns("/wx/ma/login/*") //设置不拦截的请求路径
                .excludePathPatterns("/wx/mp/login/*")  //设置不拦截的请求路径
                .excludePathPatterns("/user/mockLogin");  //设置不拦截的请求路径
    }
}
