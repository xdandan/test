package com.lanson.oa.action.order;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.OrderManageService;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class OrderManageAction {
	@Autowired
	private HttpSessionManage session;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private  OrderManageService service;
	
	@RequestMapping("order/manage.do")
	public String returnPage(HttpServletRequest request){
		 SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
			
			boolean  flag=permissionService.selectPermission(user.getId(), 1);
			if(!flag){
				return  Common.NoPermissionPage;
			}
			return "webpage/order/i_order_manage.jsp";
	}
	/**
	 * 冻结订单协定日期
	 * @param orderIds
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/order/approved.do")
	public void approvedDate(String orderIds,HttpServletRequest request,HttpServletResponse response) throws IOException{
		int i=0;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		 try {
			i= service.approvedDate(orderIds);
			if(i==0){
				response.getWriter().write("{\"success\":false}");
			}else{
				response.getWriter().write("{\"success\":true}");
			}
		} catch (Exception e) {
			response.getWriter().write("{\"success\":false}");
		}
	}
	
	@RequestMapping("/order/closeOpen.do")
	public void orderStatus(String orderIds,String status,HttpServletRequest request,HttpServletResponse response) throws IOException{
		int i=0;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		 try {
			i= service.orderStatus(orderIds,status);
			if(i==0){
				response.getWriter().write("{\"success\":false}");
			}else{
				response.getWriter().write("{\"success\":true}");
			}
		} catch (Exception e) {
			response.getWriter().write("{\"success\":false}");
		}
	}
	
	@RequestMapping("/order/sign.do")
	public void orderSign(String orderIds,String color,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		int i=0;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		System.out.println(color);
		 try {
		i= service.orderSign(orderIds, color);
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
