package com.lanson.oa.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Auth implements Serializable{
	private String authId;
	private int userId;
	private Timestamp loginTime;
	

	
	public void init() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
	//	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm∑÷ss√Î");
		setLoginTime(now);
		System.out.println(now);
	}
	
	
	
	public String getAuthId() {
		return authId;
	}
	public int getUserId() {
		return userId;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

}
