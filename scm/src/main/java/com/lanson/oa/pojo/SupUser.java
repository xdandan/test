package com.lanson.oa.pojo;

import java.io.Serializable;

public class SupUser implements Serializable{
	
	private int id;
	private String username;
	private String password;
	private int supplyId;
	private int perId;
	
	private String perName;
	
	private String supplyName;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getSupplyId() {
		return supplyId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public int getPerId() {
		return perId;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerId(int perId) {
		this.perId = perId;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}

}
