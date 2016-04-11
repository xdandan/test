package com.lanson.oa.action.order.report;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.OrderManageService;
import com.lanson.oa.service.OutputService;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;

@Controller
public class OutputAction {
	@Autowired
	private HttpSessionManage session;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private  OutputService service;

	/**
	 * 返回光学产能统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("order/output.do")
	public String returnPage(HttpServletRequest request){
		 SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
			
			boolean  flag=permissionService.selectPermission(user.getId(), 1);
			if(!flag){
				return  Common.NoPermissionPage;
			}
			return "webpage/order/output.jsp";
	}

	/**
	 * 返回光学和老花产能统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("order/output_pl.do")
	public String returnPlPage(HttpServletRequest request){
		 SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
			
			boolean  flag=permissionService.selectPermission(user.getId(), 1);
			if(!flag){
				return  Common.NoPermissionPage;
			}
			return "webpage/order/pl/output_pl.jsp";
	}


	/**
	 * 返回订单出货统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping("order/out/shipment.do")
	public String returnShipmentPage(HttpServletRequest request){
		SupUser user=(SupUser) session.getAttribute(request, Common.sessionUser);
		boolean  flag=permissionService.selectPermission(user.getId(), 1);
		if(!flag){
			return  Common.NoPermissionPage;
		}
		return "webpage/order_out/shipment_count.jsp";
	}


	/**
	 * 光学产能
	 * @param tableFlag
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("order/selectMonth.do")
	public void approvedDate(String tableFlag,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.getWriter().write(service.selectOutPut(tableFlag,Integer.toString(Common.getCurYear())));
	}
	/**
	 * 老花和光学产能
	 * @param tableFlag
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("order/selectPl.do")
	public void selectPlOutPut(String tableFlag,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	    response.getWriter().write(service.selectPlOutPut(tableFlag,Integer.toString(Common.getCurYear())));
	}

	/**
	 * 查询出货年份
	 */
	@RequestMapping("order/year.do")
	public  void selectYear(HttpServletResponse response) throws IOException {
		response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(service.selectYear("gx"));
	}

	@RequestMapping("order/shipAmont.do")
	public  void shipAmontList(HttpServletResponse response,int year,String tableFlag) throws IOException {
		response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(service.shipAmontList(tableFlag,year));
	}


	

}
