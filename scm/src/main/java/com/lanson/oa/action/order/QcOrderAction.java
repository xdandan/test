package com.lanson.oa.action.order;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanson.oa.pojo.QCRemark;
import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.service.QcOrderService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class QcOrderAction {
	@Autowired
	private  QcOrderService service;
	@Autowired
	private HttpSessionManage session;
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 返回页面
	 * @return
	 */
	@RequestMapping(value="qc/order.do")
	public String    returnPage(HttpServletRequest request){
		SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
		
		boolean  flag=permissionService.selectPermission(user.getId(), 2);
		if(flag){
			return  Common.NoPermissionPage;
		}
		return "webpage/order/order_qc_list.jsp";
	}
	/**
	 * 订单list列表
	 * @return
	 * @throws IOException 
	 */
    @RequestMapping(value="qc/orderlist.do")
    public void  list(HttpServletRequest  request ,HttpServletResponse response,String query,int start,int limit) throws IOException{
    	SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
    	response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//"{count:4,root:[{id:1,orderNo:'教师录用公示 '},{id:2,orderNo:'教师录用公示 '},{id:3,orderNo:'教师录用公示 '},{id:4,orderNo:'教师录用公示 '}]}"
	    response.getWriter().write(service.orderList(user.getSupplyId(),query, start, limit));
    }
    
    @RequestMapping(value="qc/remark.do")
    public void  saveOrUpdate(HttpServletResponse response,QCRemark qcRemark) throws IOException{
		int i=0;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		 try {
			boolean flag= service.selectSupplyWork(qcRemark.getOrderId());
			if(!flag){
				response.getWriter().write("{\"success\":true,\"msg\":1}");
			}else{
				i= service.saveOrUpdate(qcRemark);
				if(i==0){
					response.getWriter().write("{\"success\":false}");
				}else{
					response.getWriter().write("{\"success\":true}");
				}
			}
		} catch (Exception e) {
			response.getWriter().write("{\"success\":false}");
	    }
    }
    
    @RequestMapping(value="supply/view.do")
    public String  supplyView(HttpServletRequest request){
      SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
		boolean  flag=permissionService.selectPermission(user.getId(), 2);
		if(!flag){
			return  Common.NoPermissionPage;
		}
    return "webpage/order/supply_view.jsp";	
    }
    
    
    @RequestMapping(value="qc/supplyView.do")
    public void viewSaveOrUpdate(HttpServletResponse response,QCRemark qcRemark) throws IOException{
    	int i=0;
		response.setContentType("text/HTML;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		 try {
				boolean flag= service.selectSupplyWork(qcRemark.getOrderId());
				if(!flag){
					response.getWriter().write("{\"success\":true,\"msg\":1}");
				}else{
					i= service.viewSaveOrUpdate(qcRemark);
					if(i==0){
						response.getWriter().write("{\"success\":false}");
					}else{
						response.getWriter().write("{\"success\":true}");
					}
				}
		} catch (Exception e) {
			response.getWriter().write("{\"success\":false}");
	    }
    	
    }
    
    
	
	
	
	 
}
