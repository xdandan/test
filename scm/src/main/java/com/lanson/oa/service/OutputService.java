package com.lanson.oa.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lanson.oa.util.Common;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanson.eoa.dao.OrderChartDAO;
import com.lanson.eoa.dao.SupplyItemDAO;
import com.lanson.oa.pojo.ChartOrder;
import com.lanson.oa.pojo.Output;

@Service
public class OutputService {
	@Autowired
	private  OrderChartDAO dao;
	@Autowired
	private SupplyItemDAO supplyItemDAO;

	/**
	 * 查询所有出货的年份
	 * @param tableFlag
	 * @return
	 */
	public String selectYear(String tableFlag){
		List<ChartOrder> years=null;
        if(tableFlag.equals("gx")){
			years= dao.selectYear(Common.tableViewOrderOut);
		}else if (tableFlag.equals("lh")){
			years= dao.selectYear(Common.tableViewOrderOutPl);
		}
		JSONArray  arr=JSONArray.fromObject(years);
		return arr.toString();
	}


	public String shipAmontList(String tableFlag,int year){
		String tableName="";
		if (tableFlag.equals("lh")){
			tableName=Common.tableViewOrderOutPl;
		}else if (tableFlag.equals("gx")){
			tableName=Common.tableViewOrderOut;
		}
		if (tableName.equals("")||year==0){
			return "" ;
		}

		List<Integer> supplyIds=dao.selectSupplyIds(tableFlag);
		List<Output> outList=new ArrayList<Output>();

		for(int supplyId:supplyIds){
			List<ChartOrder>list= dao.selectOutCont(supplyId,year,tableName);
			Output output=new Output();
			output.setSupplyId(supplyId);
			if(tableFlag.equals("gx")){
				output.setSupplyName(supplyItemDAO.selectSupplyNameById(supplyId));
			}else if (tableFlag.equals("lh")){
				output.setSupplyName(supplyItemDAO.selectPlSupplyNameById(supplyId));
			}

			for(ChartOrder charOrder:list){
				BigDecimal b1 = new BigDecimal(Float.toString(charOrder.getTotal()));
				float monthTotal=b1.floatValue();
				BigDecimal b2 = new BigDecimal(Float.toString(charOrder.getNumber()));
				float number=b2.floatValue();
				switch ( charOrder.getMonth())
				{
					case 1 :output.setOne(monthTotal);output.setOneNumber(number);break;
					case 2 :output.setTwo(monthTotal);output.setTwoNumber(number);break;
					case 3:output.setThree(monthTotal);output.setThreeNumber(number);break;
					case 4:output.setFour(monthTotal);output.setFourNumber(number);break;
					case 5:output.setFive(monthTotal);output.setFiveNumber(number);break;
					case 6:output.setSix(monthTotal);output.setSixNumber(number);break;
					case 7:output.setSeven(monthTotal);output.setSevenNumber(number);break;
					case 8:output.setEight(monthTotal);output.setEightNumber(number);break;
					case 9:output.setNine(monthTotal);output.setNineNumber(number);break;
					case 10:output.setTen(monthTotal);output.setTenNumber(number);break;
					case 11:output.setEleven(monthTotal);output.setElevenNumber(number);break;
					case 12:output.setTw(monthTotal);output.setTwNumber(number);break;
				}
			}
			outList.add(output);
		}
		JSONArray  arr=JSONArray.fromObject(outList);
		return arr.toString();
	}


	
 /**
  *
  * @param year
  * @return
  */
	@Transactional(rollbackFor=Exception.class)
	public String selectOutPut(String tableFlag,String year){
		if (tableFlag==null||tableFlag.equals("")){
			tableFlag=Common.fesByOrderDate;
		}
		List<Integer> supplyIds=dao.selectSupplyIds(null);
		List<Output> outList=new ArrayList<Output>();
		
		for(int supplyId:supplyIds){
			List<ChartOrder>list= dao.selectOutput(year,supplyId,tableFlag);
			Output output=new Output();
			output.setSupplyId(supplyId);
			output.setSupplyName(supplyItemDAO.selectSupplyNameById(supplyId));
			for(ChartOrder charOrder:list){
				BigDecimal b1 = new BigDecimal(Float.toString(charOrder.getTotal()));
			  float monthTotal=b1.floatValue();
				BigDecimal b2 = new BigDecimal(Float.toString(charOrder.getNumber()));
				float number=b2.floatValue();
			  switch ( charOrder.getMonth())
			  {
				  case 1 :output.setOne(monthTotal);output.setOneNumber(number);break;
				  case 2 :output.setTwo(monthTotal);output.setTwoNumber(number);break;
				  case 3:output.setThree(monthTotal);output.setThreeNumber(number);break;
				  case 4:output.setFour(monthTotal);output.setFourNumber(number);break;
				  case 5:output.setFive(monthTotal);output.setFiveNumber(number);break;
				  case 6:output.setSix(monthTotal);output.setSixNumber(number);break;
				  case 7:output.setSeven(monthTotal);output.setSevenNumber(number);break;
				  case 8:output.setEight(monthTotal);output.setEightNumber(number);break;
				  case 9:output.setNine(monthTotal);output.setNineNumber(number);break;
				  case 10:output.setTen(monthTotal);output.setTenNumber(number);break;
				  case 11:output.setEleven(monthTotal);output.setElevenNumber(number);break;
				  case 12:output.setTw(monthTotal);output.setTwNumber(number);break;
			  }
			}
			outList.add(output);
		}
  	    JSONArray  arr=JSONArray.fromObject(outList);
		return arr.toString();
	}
	
	 /**
	  *
	  * @param year
	  * @return
	  */
		@Transactional(rollbackFor=Exception.class)
		public String selectPlOutPut(String tableFlag,String year){
			if (tableFlag==null||tableFlag.equals("")){
				tableFlag=Common.fesByOrderDate;
			}
			List<Integer> supplyIds=new ArrayList<Integer>();
			supplyIds.add(6);
			supplyIds.add(9);
			supplyIds.add(10);
			supplyIds.add(34);
			supplyIds.add(46);
			List<Output> outList=new ArrayList<Output>();
			
			for(int supplyId:supplyIds){
				List<ChartOrder>list=new ArrayList<ChartOrder>();
				Output output=new Output();
				if(supplyId==6||supplyId==9||supplyId==10){
					list= dao.selectOutput(year,supplyId,tableFlag);
					output.setSupplyId(supplyId);
					output.setSupplyName(supplyItemDAO.selectSupplyNameById(supplyId));
				}else{
					list=dao.selectOutputPl(year, supplyId,tableFlag);
					output.setSupplyId(supplyId);
					output.setSupplyName(supplyItemDAO.selectPlSupplyNameById(supplyId));
				}
					for(ChartOrder charOrder:list){
						BigDecimal b1 = new BigDecimal(Float.toString(charOrder.getTotal()));
					  float monthTotal=b1.floatValue();
						BigDecimal b2 = new BigDecimal(Float.toString(charOrder.getNumber()));
						float number=b2.floatValue();
					  switch ( charOrder.getMonth())
					  {
						  case 1 :output.setOne(monthTotal);output.setOneNumber(number);break;
						  case 2 :output.setTwo(monthTotal);output.setTwoNumber(number);break;
						  case 3:output.setThree(monthTotal);output.setThreeNumber(number);break;
						  case 4:output.setFour(monthTotal);output.setFourNumber(number);break;
						  case 5:output.setFive(monthTotal);output.setFiveNumber(number);break;
						  case 6:output.setSix(monthTotal);output.setSixNumber(number);break;
						  case 7:output.setSeven(monthTotal);output.setSevenNumber(number);break;
						  case 8:output.setEight(monthTotal);output.setEightNumber(number);break;
						  case 9:output.setNine(monthTotal);output.setNineNumber(number);break;
						  case 10:output.setTen(monthTotal);output.setTenNumber(number);break;
						  case 11:output.setEleven(monthTotal);output.setElevenNumber(number);break;
						  case 12:output.setTw(monthTotal);output.setTwNumber(number);break;
					  }
					}
					outList.add(output);
			
			}
	  	    JSONArray  arr=JSONArray.fromObject(outList);
			return arr.toString();
		}
		
	
	
	
	

}
