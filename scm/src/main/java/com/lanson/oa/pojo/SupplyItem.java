package com.lanson.oa.pojo;

import java.io.Serializable;

public class SupplyItem implements Serializable{
	
	private String supplyName;
	private int supplyValue;
	
	public String getSupplyName() {
		return supplyName;
	}
	public int getSupplyValue() {
		return supplyValue;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public void setSupplyValue(int supplyValue) {
		this.supplyValue = supplyValue;
	}

}
