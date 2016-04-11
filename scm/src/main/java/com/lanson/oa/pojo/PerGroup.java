package com.lanson.oa.pojo;

import java.io.Serializable;

public class PerGroup implements Serializable{
	
	private int id;
	private String perName;
	private int  userId;
	public int getId() {
		return id;
	}
	public String getPerName() {
		return perName;
	}
	public int getUserId() {
		return userId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
