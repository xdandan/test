package com.lanson.oa.dao;

import org.apache.ibatis.annotations.Param;





public interface OrderStatusDAO {
	
	public  int saveOrUpdateDate(@Param("orderId") int orderId, @Param("lockDate") String lockDate);
	
	public  int saveOrUpdateLock(@Param("orderId") int orderId, @Param("sqlStatus") String sqlStatus);
	
	public  int saveOrUpdateLevel(@Param("orderId") int orderId, @Param("colorLevel") int colorLevel);
	
}
