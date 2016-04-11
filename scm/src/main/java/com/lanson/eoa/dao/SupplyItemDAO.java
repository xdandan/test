package com.lanson.eoa.dao;

import java.util.List;

import com.lanson.oa.pojo.SupplyItem;
import org.apache.ibatis.annotations.Param;


public interface SupplyItemDAO {

	/**
	 *
	 *
	 * @return
	 */
	 List<SupplyItem> selectSupply();
	
	
	 String selectSupplyNameById(@Param("supplyId") int supplyId);
	 String selectPlSupplyNameById(@Param("supplyId") int supplyId);
}
