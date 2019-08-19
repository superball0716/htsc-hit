package com.htsc.consumer.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ManagerSearchForm;
import com.htsc.consumer.config.FundManagerConsumer;
import com.htsc.consumer.config.ManagerServiceConsumer;
import com.htsc.consumer.util.FileUtil;

@Controller
public class ManagerController {
	
	@Autowired
	ManagerServiceConsumer msc;
	
	@Autowired
	FundManagerConsumer fmc;
	
	private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
	
	private static final String filePath = "D:/img/";
	
	@GetMapping("/getManagerDetailByKeyword")
	public void getManagerDetailByKeyword(String keyword, int pageNum, int pageSize, HttpServletResponse resp) {

		JSONObject jsonObject = new JSONObject();
		String managerList = msc.getManagerDetailByKeyword(keyword, pageNum, pageSize);
		jsonObject.put("managerList", managerList);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			System.out.println("getManagerDetailByKeyword error");
		}
	}
	
	@GetMapping("/getManagerDetailBymId")
	public void getManagerDetailBymId(String mId, HttpServletResponse resp) {

		JSONObject jsonObject = new JSONObject();
		ManagerDetail md = msc.getMangerDetail(Integer.parseInt(mId));
		jsonObject.put("managerDetail", md);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			System.out.println("getManagerDetailBymId error");
		}
	}
	
	
	
	@GetMapping("/vagueSearchByManager")
	public void vagueSearchByManager(String keyword, HttpServletResponse resp) {
    	JSONObject jsonObject = new JSONObject();
    	
		List<ManagerSearchForm> userSearchList = msc.vagueSearchByManager(keyword);
		
		jsonObject.put("managerSearch", userSearchList);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			log.error("vagueSearchByManager error");

		}
	}
	
	@PostMapping("/editManager")
	public void editManager( @RequestParam(value="avatar",required=false) MultipartFile file,@RequestParam(value="mName") String mName, 
			@RequestParam(value="description") String description,@RequestParam(value="mId") String mId, HttpServletResponse resp) {
		
		String fileName = "";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		String now = df.format(new Date());
		if(Integer.parseInt(mId) > 0 ) {
			ManagerDetail md = msc.getMangerDetail(Integer.parseInt(mId));
			String jobId = md.getJobId();
			if(file == null) {
				fileName = md.getImg_url();
				msc.editManager(Integer.parseInt(mId), mName, description, fileName);

			}
			else {
				fileName = jobId + "-" + now +".png";
				msc.editManager(Integer.parseInt(mId), mName, description, "/avatar/"+fileName);
			}
			
		}
		else {
			while(true) {
				String newjobId = msc.getNewjobId();
				fileName = newjobId + "-" + now+".png";
				boolean addResult = msc.addManager(mName, newjobId, description, "/avatar/"+ fileName);
				if(addResult) {
					break;
				}
			}
		}
		
    	JSONObject jsonObject = new JSONObject();
		
        try {
        	if(file != null) {
        		FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        	}
    		
    		jsonObject.put("success",1);
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
    		
        }catch (Exception e) {
			log.error("addManager error");

		}
	}
	
	@PostMapping("/delManager")
	public void editManager(@RequestParam(value="mId") String mId, HttpServletResponse resp) {
		log.info("delManager:{}",mId);
		fmc.delFundManager(Integer.parseInt(mId));

		boolean result = msc.delManager(Integer.parseInt(mId));
		log.info("delManager after delFundManager {}",result);
		
    	JSONObject jsonObject = new JSONObject();
		
        try {
    		
    		jsonObject.put("success",result);
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
    		
        }catch (Exception e) {
			log.error("addManager error");

		}
	}
}
