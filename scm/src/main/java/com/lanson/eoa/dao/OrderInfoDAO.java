package com.lanson.eoa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lanson.oa.pojo.OrderInfo;
import com.lanson.oa.pojo.SupOrderWork;



public interface OrderInfoDAO {
	
	/**
	 *
	 *
	 * @return
	 */
	  List<OrderInfo> selectOrderBySupplyId(@Param("supplyId") int supplyId, @Param("orderNo") String orderNo, @Param("start") int start, @Param("end") int end, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("ourOrder") String ourOrder, @Param("style") String style);
	
	/**
	 *
	 * @return
	 */
	  List<OrderInfo> selectOrders(@Param("supplyId") int supplyId, @Param("orderNo") String orderNo, @Param("start") int start, @Param("end") int end);
	
	/**
	 *
	 */
	  List<OrderInfo> 	selectCommentBySupplyId(int supplyId, String orderNo, int start, int end);

	 int SaveOrUpdate(SupOrderWork supOrderWork);
}
