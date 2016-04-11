package com.lanson.oa.pojo;

import java.io.Serializable;

public class QCRemark implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private int id;
	private int orderId;
	private String qcAdress;
	private String  qcUser;
	private String check_result;
	private String  question;
	private double pass;
	private  String checkDate;
	private String qcRemark;

	
	public int getId() {
		return id;
	}
	public int getOrderId() {
		return orderId;
	}
	public String getQcRemark() {
		return qcRemark;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public void setQcRemark(String qcRemark) {
		this.qcRemark = qcRemark;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getQcAdress() {
		return qcAdress;
	}
	public String getQcUser() {
		return qcUser;
	}
	public String getCheck_result() {
		return check_result;
	}
	public String getQuestion() {
		return question;
	}
	public double getPass() {
		return pass;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setQcAdress(String qcAdress) {
		this.qcAdress = qcAdress;
	}
	public void setQcUser(String qcUser) {
		this.qcUser = qcUser;
	}
	public void setCheck_result(String checkResult) {
		check_result = checkResult;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setPass(double pass) {
		this.pass = pass;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

}
