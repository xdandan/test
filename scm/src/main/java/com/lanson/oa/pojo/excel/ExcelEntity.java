package com.lanson.oa.pojo.excel;

import java.util.Date;

public class ExcelEntity {
	/**
	 * "客户型号","我司型号","订单号码","品名","数量/副","含税单价(元/副)","金额/元","备注"
	 */
    private String itemStyle;
    private String ourStyle;
    private String orderNo;
    private String proName;
    private double outNumber;
    private double orderPrice;
    private double totalMoney;
    private String remark;
    
    
	public String getItemStyle() {
		return itemStyle;
	}
	public String getOurStyle() {
		return ourStyle;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getProName() {
		return proName;
	}
	public double getOutNumber() {
		return outNumber;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setItemStyle(String itemStyle) {
		this.itemStyle = itemStyle;
	}
	public void setOurStyle(String ourStyle) {
		this.ourStyle = ourStyle;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public void setOutNumber(double outNumber) {
		this.outNumber = outNumber;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
