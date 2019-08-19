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

public class TokenInterceptor implements HandlerInterceptor{
	
	private static final int EXPIRE = 12*3600;
	
	private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String token = (String)request.getHeader("Authorization");
        log.info("interceptor request :{}",request.getContextPath());
    	
        if(token == null || CacheUtils.getBean(token, UserCache.class) == null) {
        	JSONObject jsonObject = new JSONObject();
        	
        	jsonObject.put("Expire", 1);
        	
        	response.setContentType("text/html;charset=UTF-8");
            try {
    			response.getWriter().println(jsonObject.toJSONString());
    	        response.getWriter().close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			System.out.println("error");
    		}
            return false;
        }
        else {
        	CacheUtils.expire(token, EXPIRE);
        	return true;
        }
		      
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
