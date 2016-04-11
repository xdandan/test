package com.lanson.oa.action.order;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanson.oa.pojo.SupOrderWork;
import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.OrderService;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class OrderAction {
@Autowired
private  OrderService orderService;
@Autowired
private HttpSessionManage session;
@Autowired
private PermissionService permissionService;
	
	@RequestMapping(value="order.do")
	public String order(HttpServletRequest  request){
     SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
		boolean  flag=permissionService.selectPermission(user.getId(), 2);
		if(!flag){
			return  Common.NoPermissionPage;
		}
		return "webpage/order/order_manage.jsp";
	}

	 @RequestMapping(value="order/orderlist.do")
	    public  void orderList(HttpServletRequest request,HttpServletResponse response,String startDate,String endDate,String ourOrder,String orderNo,int start,int limit) throws IOException{
            // System.out.println(start+","+limit);
		SupUser user= (SupUser) session.getAttribute(request, Common.sessionUser);
	    	response.setContentType("text/HTML;charset=UTF-8");//
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		    response.getWriter().write(orderService.orderList(user.getSupplyId(), orderNo, start, limit,startDate,endDate,ourOrder,""));
	    }
	 
	 @RequestMapping(value="order/update.do")
	    public  void orderWorkUpdate(SupOrderWork sWork,String lockDate,double orderNumber,HttpServletResponse response) throws IOException {
		int i;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		 try {
			 if(lockDate.equals("lock")){
				 sWork.setSupDate(null);
			 }
			i= orderService.orderWorkUpdate(sWork,orderNumber);
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
