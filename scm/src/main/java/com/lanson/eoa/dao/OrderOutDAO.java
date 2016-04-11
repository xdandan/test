package com.lanson.eoa.dao;

import com.lanson.oa.pojo.ChartOrder;
import com.lanson.oa.pojo.out.OrderOut;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.annotation.Order;


import java.util.List;

/**
 * Created by fi25 on 15-10-16.
 */
public interface OrderOutDAO {

    List<OrderOut> selectOutsByCon(@Param("start")int start,@Param("end")int end,@Param("ConNumber")String ConNumber,@Param("orderOut")String orderOut,@Param("viewInfo")String viewInfo);


    int selectCounOuts(@Param("ConNumber")String ConNumber,@Param("orderOut")String orderOut,@Param("viewInfo")String viewInfo);

    List<Integer> selectSupplyIds(@Param("ConNumber")String ConNumber,@Param("orderOut")String orderOut,@Param("viewInfo")String viewInfo );


    List<OrderOut> findListByConNumber(@Param("supplyId")int supplyId,@Param("ConNumber")String ConNumber,@Param("orderOut")String orderOut,@Param("viewInfo")String viewInfo);

}
