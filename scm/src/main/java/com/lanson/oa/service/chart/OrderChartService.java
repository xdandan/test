package com.lanson.oa.service.chart;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanson.eoa.dao.OrderChartDAO;
import com.lanson.oa.pojo.ChartOrder;

@Service
public class OrderChartService {
	@Autowired
	private OrderChartDAO dao;
	
	
	public String  chartOrderByYeah(String year,int supplyId){
		   List<ChartOrder> list=new ArrayList<ChartOrder>();
	    	list=dao.selectChartOrder(year, supplyId);
	   	    JSONArray  arr=JSONArray.fromObject(list);
	    	return arr.toString();
	}
	
	public String  chartOrderByWeek(String year,Integer supplyId){
		   List<ChartOrder> list=new ArrayList<ChartOrder>();
	    	list=dao.selectDeliveryByWeek(year, supplyId);
	   	    JSONArray  arr=JSONArray.fromObject(list);
	    	return arr.toString();
	}


}
