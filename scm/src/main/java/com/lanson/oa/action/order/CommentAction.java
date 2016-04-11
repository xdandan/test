package com.lanson.oa.action.order;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanson.oa.pojo.SupOrderWork;
import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.CommentService;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class CommentAction {
	@Autowired
	private HttpSessionManage session;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private  CommentService service;
	
	/**
	 * 供应商批注
	 * @param request
	 * @return
	 */
	@RequestMapping(value="supply/comment.do")
	public  String  returnPage(HttpServletRequest request){
       SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
		
		boolean  flag=permissionService.selectPermission(user.getId(), 2);
		if(!flag){
			return  Common.NoPermissionPage;
		}
		return "webpage/order/order_comment.jsp";
	}
	
	@RequestMapping(value="our/comment.do")
	public  String  returnOurPage(HttpServletRequest request){
		return "webpage/order/our_comment.jsp";
	}
	
	
	
	
	
	@RequestMapping(value="qc/supplyComment.do")
	public void  commentList(HttpServletRequest request,HttpServletResponse response,String query,int start,int limit) throws IOException{
		SupUser user= (SupUser) session.getAttribute(request, Common.sessionUser);
	    	response.setContentType("text/HTML;charset=UTF-8");//
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			//{count:2,root:[{id:1,orderNo:'教师录用公示 '},{id:2,orderNo:'教师录用公示 '},{id:3,orderNo:'教师录用公示 '},{id:4,orderNo:'教师录用公示 '}]}
		    response.getWriter().write(service.orderList(user.getSupplyId(), query, start, limit));
	    }
	
	@RequestMapping(value="supply/commentSave.do")
	public  void commentSave(HttpServletResponse response,SupOrderWork sWork) throws IOException{
		int i=0;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		 try {
			i= service.saveOrUpdate(sWork);
			if(i==0){
				response.getWriter().write("{\"success\":false}");
			}else{
				response.getWriter().write("{\"success\":true}");
			}
		} catch (Exception e) {
			response.getWriter().write("{\"success\":false}");
		}
	}
		


}
