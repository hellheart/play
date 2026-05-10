package com.huige.learning.config;

import com.huige.learning.auth.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(tokenBlacklist))
                .addPathPatterns("/user/**", "/api/bilibili/**")
                .excludePathPatterns("/auth/**");
    }
}
