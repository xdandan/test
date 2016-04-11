package com.lanson.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.lanson.oa.pojo.SupOrderWork;




public interface SupOderWorkDAO {
	
	SupOrderWork selectSupOrderById(int orderId);
	
	
        int   workSaveOrUpdate(SupOrderWork supOrderWork);
      
      int   commentSaveOrUpdate(SupOrderWork supOrderWork);
      
       String selectReplyDateById(int orderId);
      
      int  insertCurDate(@Param("curDate") String curDate, @Param("orderId") int orderId);
      
       String selectCurDateById(int orderId);
}
