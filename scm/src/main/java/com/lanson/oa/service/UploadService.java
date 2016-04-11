package com.lanson.oa.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanson.oa.dao.AttendInfoDAO;
import com.lanson.oa.dao.AttendTimeDAO;
import com.lanson.eoa.dao.UserDAO;
import com.lanson.oa.pojo.AttendInfo;
import com.lanson.oa.pojo.AttendTime;
import com.lanson.oa.util.ExcelReader;
@Service
public class UploadService {
	@Autowired
	private  AttendInfoDAO aInfoDAO;
	@Autowired
	private AttendTimeDAO attendTimeDAO;
	@Autowired
	private UserDAO userDAO;
	
	/**
	 * 在泛微数据库中查询人员ID
	 * @param workcode
	 * @return
	 */
	@Transactional(value="eoa",readOnly=true,rollbackFor=Exception.class)
	public  int   selectIdByWorkCode(String   workcode) throws Exception{
			return userDAO.selectIdByWorkCode(workcode);
	}
	
	
	/**
	 * 
	 * @param filePath
	 * 解析上传的考勤模板文件，并写入数据库中
	 */
	@Transactional(value="loa",readOnly = true,rollbackFor=Exception.class)
	public void  anlyExcel(String filePath){
		
		try {
			Map  map1=new  HashMap();
		  ExcelReader excelReader = new ExcelReader();
		  InputStream is = new FileInputStream(filePath);
		   Map<Integer, String> map = excelReader.readExcelContent(is);
		   
		     for (int i = 1; i <= map.size(); i++) {
		    	 AttendInfo attendInfo=new AttendInfo();
		    	 List<AttendTime> listTimes=new ArrayList<AttendTime>();
	            	String userNo="";
	            	String  checkDate="";
	            	String  checkTime="";
	            	String info=map.get(i);
	            	String[] infos= info.split(",");
	            	
	    
	            	for(String str:infos){
	            		if(str.startsWith("工")){
			            	try {
			                		userNo=str.substring(3, str.lastIndexOf("姓名:")).trim();
			                		attendInfo.setUserId(selectIdByWorkCode(userNo));
			            		} catch (Exception e) {
									System.out.println("工号"+userNo+"不存在");
									return;
								} 	
	                	}
	         		if(str.contains("/")&&!str.contains(":")){
	         			checkDate=str.replace("/", "-").trim();
	         			attendInfo.setAttendDate(checkDate);
	            		}
	         		if(str.contains("未分析")){
	         			checkTime+=str.substring(0,str.lastIndexOf("未分析")).trim()+",";
	         		}
	            	}
	            	
	            	if(!userNo.equals("")&&userNo!=null){
	            		AttendInfo attendInfo1=aInfoDAO.selectId(attendInfo);
	            		if(attendInfo1==null){
	            			aInfoDAO.addInfo(attendInfo);
	            		}else{
	            			attendInfo.setId(attendInfo1.getId());
	            		}
	            		 String[] times=checkTime.split(",");
	            		 for(String time:times){
	            			 if(attendTimeDAO.selectTime(attendInfo.getId(),time)==0){
	            				 AttendTime attendTime=new AttendTime();
	            				 attendTime.setAttendId(attendInfo.getId());
	            				 attendTime.setAttendTime(time);
	            				 listTimes.add(attendTime);
	            			 }
	            		 }
	            		 if(listTimes.size()!=0){
	            			 attendTimeDAO.batchAddTime(listTimes);
	            		 }
	            		 
	            	    }
	            	
	            	
		     }  
		     
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	

}
