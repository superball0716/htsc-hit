package com.htsc.consumer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htsc.bean.LoginForm;
import com.htsc.bean.UserCache;
import com.htsc.bean.ManagerSearchForm;
import com.htsc.consumer.config.ManagerServiceConsumer;
import com.htsc.consumer.config.UserServiceConsumer;
import com.htsc.consumer.redis.CacheUtils;
import com.htsc.consumer.util.ResultCode;

@Controller
public class LoginController {
	@Autowired
	UserServiceConsumer usc;
	
	@Autowired
	ManagerServiceConsumer msc;
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private static final int EXPIRE = 12*3600;
	
	@PostMapping("/login")
	@ResponseBody
    public void loginForm(@RequestBody LoginForm lm,HttpServletRequest request,HttpServletResponse resp) {
		String userName = lm.getUserName();
		String passwd = lm.getPassword();
		log.info("login validate userName:{},password:{}",userName,passwd);	
		UserCache userCache = usc.LoginValidate(userName, passwd);
		
    	resp.setContentType("text/html;charset=UTF-8");
        try {
			if(userCache != null) {
				log.info("login validate result not null");	
				CacheUtils.saveBean(userCache.getToken(), userCache, EXPIRE);
				String token = (String)request.getHeader("Authorization");
				if (token != null && token != "") {
					log.info("del token {}",token);
					CacheUtils.delKey(token);
				}
				resp.getWriter().println(JSON.toJSONString(userCache));
			}
			else {
				//判断用户名是否存在
		    	JSONObject jsonObject = new JSONObject();

				if(usc.userExist(userName) == true) {
					jsonObject.put("failReason", ResultCode.WRONG_PASSWORD);
				}
				else {
					jsonObject.put("failReason", ResultCode.USER_NOT_FOUND);
				}
				resp.getWriter().println(jsonObject.toJSONString());
			}
	        resp.getWriter().close();

		} catch (IOException e) {
			log.error("user login error");
		}
    }
	

	
	@GetMapping("/userExist")
	@ResponseBody
    public void userExist(String userName,HttpServletResponse resp) {
		log.info("userExist:{}",userName);
    	resp.setContentType("text/html;charset=UTF-8");
    	try {
	    	JSONObject jsonObject = new JSONObject();

	    	if(!usc.userExist(userName)) {
				jsonObject.put("Exist", 0);
			}
	    	else {
				jsonObject.put("Exist", 1);
	    	}
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
    	}catch (IOException e) {
			log.error("user Exist error");
		}
    }

	@PostMapping("/register")
	@ResponseBody
    public void register(@RequestBody LoginForm lm,HttpServletRequest request,HttpServletResponse resp) {
		String userName = lm.getUserName();
		String passwd = lm.getPassword();
		log.info("register userName:{},password:{}",userName,passwd);	
		
    	resp.setContentType("text/html;charset=UTF-8");
    	try {
	    	JSONObject jsonObject = new JSONObject();

	    	if(!usc.userExist(userName)) {
	    		if(usc.addUser(userName, passwd)) {
	    			jsonObject.put("success",1);
	    		}
	    		else {
					jsonObject.put("failReason", ResultCode.USER_ALREADY_EXIST);
	    		}
			}
	    	else {
				jsonObject.put("failReason", ResultCode.USER_ALREADY_EXIST);
	    	}
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
    	}catch (IOException e) {
			log.error("user register error");
		}
    }
}
