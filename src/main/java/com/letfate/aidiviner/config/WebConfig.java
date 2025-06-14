package com.letfate.aidiviner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import com.letfate.aidiviner.config.Interceptor.CaptchaInterceptor;
import com.letfate.aidiviner.config.Interceptor.auth.AuthInterceptor;
import com.letfate.aidiviner.config.Interceptor.ToolInterceptor;
import com.letfate.aidiviner.config.Interceptor.PreInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private PreInterceptor pre;
    @Autowired
    private CaptchaInterceptor captcha;
    @Autowired
    private AuthInterceptor auth;
    @Autowired
    private ToolInterceptor tool;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.pre).addPathPatterns("/**");
        registry.addInterceptor(this.captcha).addPathPatterns("/user/register_login");
        registry.addInterceptor(this.auth).addPathPatterns("/user/**", "/tool/**").excludePathPatterns("/user/register_login");
        registry.addInterceptor(this.tool).addPathPatterns("/tool/**");
    }
}