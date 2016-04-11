package com.lanson.oa.pojo;

import java.io.Serializable;

/**
 * 
 * @author fi25
 *³öÇÚÊ±¼ä
 */
public class AttendTime implements Serializable {
	
   private int  attendId;
   private String attendTime;
   private AttendInfo attendInfo;
   
   
public int getAttendId() {
	return attendId;
}
public String getAttendTime() {
	return attendTime;
}
public void setAttendId(int attendId) {
	this.attendId = attendId;
}
public void setAttendTime(String attendTime) {
	this.attendTime = attendTime;
}
public AttendInfo getAttendInfo() {
	return attendInfo;
}
public void setAttendInfo(AttendInfo attendInfo) {
	this.attendInfo = attendInfo;
}
}
