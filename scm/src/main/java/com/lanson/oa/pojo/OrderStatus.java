package com.lanson.oa.pojo;

public class OrderStatus {
	private  int orderId;
	private String lockDate;
	private String lockOrder;
	private int orderLevel;
	private int count;
	
	public int getOrderId() {
		return orderId;
	}
	public String getLockDate() {
		return lockDate;
	}
	public String getLockOrder() {
		return lockOrder;
	}
	public int getOrderLevel() {
		return orderLevel;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}
	public void setLockOrder(String lockOrder) {
		this.lockOrder = lockOrder;
	}
	public void setOrderLevel(int orderLevel) {
		this.orderLevel = orderLevel;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
