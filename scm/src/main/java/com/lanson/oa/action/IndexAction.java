package com.lanson.oa.action;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;





@Controller
public class IndexAction {
	@RequestMapping(value="index.do")
	public String index(HttpServletRequest request){
		return "index.html";
	}
	@RequestMapping(value="user/online.do")
	public void online(HttpServletResponse response) throws IOException{
		response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//{count:2,root:[{id:1,orderNo:'��ʦ¼�ù�ʾ '},{id:2,orderNo:'��ʦ¼�ù�ʾ '},{id:3,orderNo:'��ʦ¼�ù�ʾ '},{id:4,orderNo:'��ʦ¼�ù�ʾ '}]}
	    response.getWriter().write("online:Y");
	}
	

	@RequestMapping(value="index.jsp")
	public void rootIndex(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect("index.do");
	}
	
	@RequestMapping(value="index/index.do")
	public String welcomeIndex(){
		return "webpage/index/index.html";
	}
	
}
