package com.lanson.oa.pojo;

import java.io.Serializable;
import java.util.List;


/**
 * @author fi25
 *³öÇÚÐÅÏ¢
 */
public class AttendInfo  implements Serializable{
	private int id;
	private int  userId;
	private  String attendDate;
	private  List<AttendTime> times;
	
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public String getAttendDate() {
		return attendDate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setAttendDate(String attendDate) {
		this.attendDate = attendDate;
	}
	public List<AttendTime> getTimes() {
		return times;
	}
	public void setTimes(List<AttendTime> times) {
		this.times = times;
	}
}
