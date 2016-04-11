package com.lanson.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanson.oa.action.order.OrderAction;
import com.lanson.oa.dao.OrderStatusDAO;
import com.lanson.oa.util.Common;

@Service
public class OrderManageService {
	@Autowired
	private  OrderStatusDAO dao;
	
	
	@Transactional(rollbackFor=Exception.class)
	public int  approvedDate(String orderIds){
		int i=0;
		String[]  arryOrderIds=(orderIds.substring(0,orderIds.length()-1)).split(",");
		for(String  orderId:arryOrderIds){
			i+=dao.saveOrUpdateDate(Integer.parseInt(orderId), Common.lockDate);
		}
		return i;
	}
	
	public int orderStatus(String orderIds,String status){
      int i=0;
     String sqlStatus="";
      if(status.equals(Common.orderClose)){
    	  sqlStatus=Common.orderClose;
      }else{
    	  sqlStatus=Common.orderActive;
      }
      String[]  arryOrderIds=(orderIds.substring(0,orderIds.length()-1)).split(",");
		for(String  orderId:arryOrderIds){
			i+=dao.saveOrUpdateLock(Integer.parseInt(orderId),sqlStatus);
		}
		
      return i;		  
	}
	
	public int orderSign(String orderIds,String color){
		   int i=0;
		   int colorLevel=0;
		   if(color.equals("3")){
			   colorLevel=3;
		   }else if(color.equals("2")){
			   colorLevel=2;
		   }else{
			   colorLevel=1;
		   }
		   
		      String[]  arryOrderIds=(orderIds.substring(0,orderIds.length()-1)).split(",");
				for(String  orderId:arryOrderIds){
					i+=dao.saveOrUpdateLevel(Integer.parseInt(orderId),colorLevel);
				}
				
		      return i;	
	}

}
