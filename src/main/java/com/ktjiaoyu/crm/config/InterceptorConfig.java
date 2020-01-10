package com.ktjiaoyu.crm.config;


import com.ktjiaoyu.crm.web.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private String[] excludePathPatterns;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] pathPatterns = {"/**"};    //拦截路径规则
        //String[] excludePathPatterns = {"/","/login","/dologin"};  //排除路径规则
        String[] excludePathPatterns = {"/","/login","/dologin","/css/**","/fonts/**","/images/**","/js/**","/localcss/**","/localjs/**"};  //排除路径规则
        registry.addInterceptor(new AuthorizationInterceptor())     //注册拦截器
                .addPathPatterns(pathPatterns)  //添加路径规则
                .excludePathPatterns(excludePathPatterns);      //添加排除路径规则
    }
}
