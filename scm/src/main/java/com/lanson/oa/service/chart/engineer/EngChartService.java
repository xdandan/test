package com.lanson.oa.service.chart.engineer;

import com.lanson.eoa.dao.OrderChartDAO;
import com.lanson.oa.pojo.ChartOrder;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EngChartService {
	@Autowired
	private OrderChartDAO dao;

	/**
	 * 订单每月数量
	 * @param tableFlag
	 * @param year
	 * @return
	 */
	public String  engChartByYeah(String tableFlag,String year){
		   List<ChartOrder> list=new ArrayList<ChartOrder>();
	    	list=dao.selectEngOrder(tableFlag,year);
	   	    JSONArray  arr=JSONArray.fromObject(list);
	    	return arr.toString();
	}

	/**
	 * 查询所有的年份
	 * @param tableFlag
	 * @return
	 */
	public String selectYear(String tableFlag){
		List<ChartOrder> years=null;

			years= dao.selectEngYear(tableFlag);
		JSONArray  arr=JSONArray.fromObject(years);
		return arr.toString();
	}

	/**
	 * 工程师的工作量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String selectWorkGroupByName(String startDate,String endDate){
		List<ChartOrder> list=null;
		list= dao.selectWorkByName(startDate,endDate);
		for(ChartOrder c:list){
			if (c.getName()==null||c.getName().equals("")) {
				c.setName("未分配:" + (int) c.getTotal());
			}else {
				c.setName(c.getName()+":" + (int)c.getTotal());
			}

		}
		JSONArray  arr=JSONArray.fromObject(list);
		return arr.toString();
	}



	


}
