package com.htsc.consumer.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 20160216 on 2018/2/8.
 */
@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/*").
        	excludePathPatterns("/login").excludePathPatterns("/register").excludePathPatterns("/userExist");
        
        
        registry.addInterceptor(new RoleInterceptor()).addPathPatterns("/editFund").
        	addPathPatterns("/editManager").addPathPatterns("/delManager");
    }
    
    
}
