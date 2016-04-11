package com.lanson.eoa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lanson.oa.pojo.ChartOrder;



public interface OrderChartDAO {

    List<ChartOrder>  selectChartOrder(@Param("year") String year, @Param("supplyId") int supplyId);
    List<ChartOrder>   selectDeliveryByWeek(@Param("year") String year, @Param("supplyId") Integer supplyId);

    List<ChartOrder>  selectOutput(@Param("year") String year, @Param("supplyId") int supplyId,@Param("date")String date);
    List<ChartOrder>  selectOutputPl(@Param("year") String year, @Param("supplyId") int supplyId,@Param("date")String date);

    List<Integer> selectSupplyIds(@Param("belong") String belong);

    List<ChartOrder> selectYear(@Param("tableName")String tableName);

    List<ChartOrder> selectOutCont(@Param("supplyId")int supplyId,@Param("year")int year,@Param("tableName")String tableName);

    /**
     * OA工程订单量统计
     * @param year
     * @return
     */
    List<ChartOrder>  selectEngOrder(@Param("tableFlag")String tableFlag,@Param("year") String year);
    /**
     * OA工程订单量年份
     * @param tableFlag
     * @return
     */
    List<ChartOrder> selectEngYear(@Param("tableFlag")String tableFlag);

    List<ChartOrder> selectWorkByName(@Param("startDate")String startDate,@Param("endDate")String endDate);

}
