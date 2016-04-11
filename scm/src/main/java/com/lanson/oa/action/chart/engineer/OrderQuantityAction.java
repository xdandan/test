package com.lanson.oa.action.chart.engineer;

import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.chart.OrderChartService;
import com.lanson.oa.service.chart.engineer.EngChartService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class OrderQuantityAction {
	@Autowired
	private HttpSessionManage session;
	@Autowired
	private EngChartService service;

	/**
	 * 订单页面地址
	 * @param model
	 * @return
	 */
	@RequestMapping(value="chart/eng/quantity.do")
	public String returnPage(ModelMap model){
		model.addAttribute("Year", Common.getCurYear());
		return "webpage/chart/engineer/order_quantity_chart.jsp";
	}

	/**
	 * 工程师工作量页面地址
	 * @param model
	 * @return
	 */
	@RequestMapping(value="chart/eng/work.do")
	public String returnWorkPage(ModelMap model){
		model.addAttribute("Year", Common.getCurYear());
		return "webpage/chart/engineer/eng_workload.jsp";
	}

	/**
	 * 获取不用类型订单数量
	 * @param request
	 * @param response
	 * @param tableFlag
	 * @param year
	 * @throws IOException
	 */
	@RequestMapping(value="chart/eng/qlist.do")
	public  void  monthOrderList(HttpServletRequest request,HttpServletResponse response,String tableFlag,String year) throws IOException{

	    	response.setContentType("text/HTML;charset=UTF-8");//
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		if (year==null||year.equals("")){
			year = Integer.toString(Common.getCurYear());
		}
		if (tableFlag==null||tableFlag.equals("")){
			tableFlag = "td";
		}
		    response.getWriter().write(service.engChartByYeah(tableFlag,year));
	}

	/**
	 * 查询订单所有年份
	 */
	@RequestMapping("eng/year.do")
	public  void selectYear(HttpServletResponse response,String tableFlag) throws IOException {
		response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		if (tableFlag==null||tableFlag.equals("")){
			tableFlag = "td";
		}
		response.getWriter().write(service.selectYear(tableFlag));
	}


	@RequestMapping("chart/eng/workgroup.do")
	public  void selectWorkGroupByName(HttpServletResponse response,String startDate,String endDate) throws IOException {
		response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		response.getWriter().write(service.selectWorkGroupByName(startDate,endDate));
	}

}
