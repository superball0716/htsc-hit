package com.htsc.consumer.Interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.htsc.bean.UserCache;
import com.htsc.consumer.redis.CacheUtils;

public class RoleInterceptor implements HandlerInterceptor{
	
	private static final int EXPIRE = 12*3600;
	
	private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String token = (String)request.getHeader("Authorization");
    	
        if(token == null || CacheUtils.getBean(token, UserCache.class) == null) {
            return false;
        }
        else {
        	UserCache uc = CacheUtils.getBean(token, UserCache.class);
        	if(uc.getRole() == 1) {
        		return true;
        	}
        	else {
        		return false;
        	}
        }
		      
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

