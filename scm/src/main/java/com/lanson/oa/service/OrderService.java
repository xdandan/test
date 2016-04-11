package com.lanson.oa.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanson.eoa.dao.OrderInfoDAO;
import com.lanson.oa.dao.SupOderWorkDAO;
import com.lanson.oa.pojo.OrderInfo;
import com.lanson.oa.pojo.OrderInfoJson;
import com.lanson.oa.pojo.SupOrderWork;
import com.lanson.oa.util.Common;

@Service
public class OrderService {
	@Autowired
	private OrderInfoDAO  dao;

	@Autowired
	private SupOderWorkDAO sDao;
	
	@Transactional(rollbackFor=Exception.class)
	public  int orderWorkUpdate(SupOrderWork supOrderWork,double orderNumber){
		
		if(supOrderWork.getReplyDate1().equals("")||supOrderWork.getReplyDate1()==null){
			supOrderWork.setReplyDate1(supOrderWork.getReplyDate2());
			supOrderWork.setReplyDate2(null);
		}else{
			String sqlReplyDate2=sDao.selectReplyDateById(supOrderWork.getOrderId());
			if(sqlReplyDate2!=null&&!sqlReplyDate2.equals("")){
				supOrderWork.setReplyDate1(sqlReplyDate2);
			}
		}
		if((supOrderWork.getOutNumber()/orderNumber)>=0.9){
			String sqlCurDate=sDao.selectCurDateById(supOrderWork.getOrderId());
			if(sqlCurDate==null||sqlCurDate.equals("")){
				sDao.insertCurDate(Common.getCurDate(), supOrderWork.getOrderId());
			}
		}
		return sDao.workSaveOrUpdate(supOrderWork);
	}
   
	public String  orderList(int userid,String orderNo,int start,int end,String startDate,String endDate,String ourOrder,String style){
		if(orderNo==null){
			orderNo="";
		}
		if(!ourOrder.equals("")&&ourOrder!=null){
			ourOrder=new StringBuilder("%").append(ourOrder).append("%").toString();
		}
		if(!style.equals("")&&style!=null){
			style=new StringBuilder("%").append(style).append("%").toString();
		}
		
		String searchText = new StringBuilder("%").append(orderNo).append("%").toString();
		   List<OrderInfo> list=new ArrayList<OrderInfo>();
	    	list=dao.selectOrderBySupplyId(userid,searchText,start+1,start+end,startDate,endDate,ourOrder,style);
	    	OrderInfoJson orderInfoJson=new OrderInfoJson();
	    	orderInfoJson.setRoot(list);
	    	if(list==null||list.size()==0){
	    		orderInfoJson.setCount(0);
	    	}else{
	    		orderInfoJson.setCount(list.get(0).getCount());
	    	}
	   	    JSONObject  arr=JSONObject.fromObject(orderInfoJson);
	    	return arr.toString();
	}
	
}
