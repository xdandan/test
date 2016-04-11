package com.lanson.oa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {
	public static String sessionUser="scm-user";
	
	public static String authId="lanson-authId";
	
	public static String  lockDate="lock"; 
	
	public static String orderActive="active";
	
	public static String orderClose="close";

	public static String tableOrderOut="buss_orderout";

	public static String tableViewInfo="View_Sinal_Info";

	public static String tableOrderOutPl="buss_orderout_pl";

	public static String tableViewInfoPl="View_Sinal_Info_pl";

	public static String tableViewOrderOut="View_order_out";

	public static String tableViewOrderOutPl="View_order_out_pl";

	public static String tableFlag="pl";

	public static String fesByOrderDate="xdrq";

	public static String fesByOrderCW="cw";





	public static String  NoPermissionPage="403.jsp";
	public static int  cookieTime= 60*60*24*30;
	
	 public  static int    getCurYear(){
	     Calendar now = Calendar.getInstance();  
         return now.get(Calendar.YEAR);
   }
	
	 public static String getCurDate(){
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	 }
	

}
