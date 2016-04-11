package com.lanson.oa.action.out;

import com.lanson.oa.pojo.SupOrderWork;
import com.lanson.oa.pojo.SupUser;
import com.lanson.oa.service.OrderService;
import com.lanson.oa.service.PermissionService;
import com.lanson.oa.service.out.OutListService;
import com.lanson.oa.util.Common;
import com.lanson.oa.util.HttpSessionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class OutListAction {
@Autowired
private OutListService outListService;



	
	@RequestMapping(value="order/out/list.do")
	public String order(){
		return "webpage/order_out/orderOutList.jsp";
	}

	/**
	 * 老花
	 * @return
	 */
	@RequestMapping(value = "order/out/pl/list.do")
	public String orderPl(){
		return "webpage/order_out/orderOutPlList.jsp";
	}

	/**
	 * 光学
	 * @param response
	 * @param query
	 * @param start
	 * @param limit
	 * @throws IOException
	 */

	 @RequestMapping(value="orderout/list.do")
	    public  void orderList(HttpServletResponse response,String query,int start,int limit) throws IOException{

	    	response.setContentType("text/HTML;charset=UTF-8");//
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		    response.getWriter().write(outListService.outOrderList(query,start,limit,""));
	    }

	/**
	 * 老花
	 * @param response
	 * @param query
	 * @param start
	 * @param limit
	 * @throws IOException
	 */
	@RequestMapping(value="/orderout/pl/list.do")
	public  void orderPlList(HttpServletResponse response,String query,int start,int limit) throws IOException{
		response.setContentType("text/HTML;charset=UTF-8");//
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.getWriter().write(outListService.outOrderList(query,start,limit,Common.tableFlag));
	}



	/**光学
	 * 导出excel
	 * @param response
	 * @param query
	 */
	@RequestMapping(value="/order/out/export.do")
	public void excelExport(HttpServletResponse response,String query){
				outListService.exportExcel(query,response,"");
	}


	/**老花
	 * 导出excel
	 * @param response
	 * @param query
	 */
	@RequestMapping(value="/order/out/pl/export.do")
	public void excelPlExport(HttpServletResponse response,String query){
		outListService.exportExcel(query,response,Common.tableFlag);
	}


	 
}
