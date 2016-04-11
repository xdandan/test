package com.lanson.oa.action.chart;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.chart.OrderChartService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class OrderDeliveryAction {
	@Autowired
	private HttpSessionManage session;
	@Autowired
	private OrderChartService service;
	
	@RequestMapping(value="chart/delivery.do")
	public String returnPage(ModelMap model){
		model.addAttribute("Year", Common.getCurYear());
		return "webpage/chart/order_delivery_chart.jsp";
	}
	
	@RequestMapping(value="chart/deliverryList.do")
	public  void  monthOrderList(HttpServletRequest request,HttpServletResponse response,Integer supplyId) throws IOException{
		SupUser user= (SupUser) session.getAttribute(request, Common.sessionUser);
	    	response.setContentType("text/HTML;charset=UTF-8");//
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			//{count:2,root:[{id:1,orderNo:'教师录用公示 '},{id:2,orderNo:'教师录用公示 '},{id:3,orderNo:'教师录用公示 '},{id:4,orderNo:'教师录用公示 '}]}
		    response.getWriter().write(service.chartOrderByWeek(Integer.toString(Common.getCurYear()),supplyId));
	}

}
