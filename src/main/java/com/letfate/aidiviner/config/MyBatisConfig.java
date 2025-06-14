package com.letfate.aidiviner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.mybatis.spring.annotation.MapperScan;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

@Configuration
@MapperScan("fun.diviner")
public class MyBatisConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusPageInterceptorBean() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}