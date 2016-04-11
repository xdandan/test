package com.lanson.oa.pojo;

import java.io.Serializable;

public class OrderInfo implements Serializable{
	/**
	 * 
	 */
	private static long serialVersionUID = 1L;
	private int id;
	private  String orderNo ;//������
	private  String itemNo;//�ͻ�����
	private   int  supplyId;//供应商id

	private  String style;//�ͺ�
	private   String  orderColor;//��ɫ
	private double  price;//����
	private   double orderNumber;//�µ�����
	private  String  orderDate;//�µ�����
	private   String  deliveryDate;//��������

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	private int partId;//品名
	private String  partName;

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	private int count;
	
	private  PerGroup   perGroup;
	private   SupOrderWork supOrderWork;
	private   OrderStatus orderStatus;
	private  SupplyItem supplyItem;

//

    
	//QC
	private String qcAdress;
	private String  qcUser;
	private String check_result;
	private String  question;
	private double pass;
	private  String checkDate;
	private String qcRemark;
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public String getItemNo() {
		return itemNo;
	}
	public int getSupplyId() {
		return supplyId;
	}
	public String getStyle() {
		return style;
	}
	public String getOrderColor() {
		return orderColor;
	}
	public double getOrderNumber() {
		return orderNumber;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public void setOrderColor(String orderColor) {
		this.orderColor = orderColor;
	}
	public void setOrderNumber(double orderNumber) {
		this.orderNumber = orderNumber;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	
	public String getQcRemark() {
		return qcRemark;
	}
	public void setQcRemark(String qcRemark) {
		this.qcRemark = qcRemark;
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
	
	public PerGroup getPerGroup() {
		return perGroup;
	}
	public void setPerGroup(PerGroup perGroup) {
		this.perGroup = perGroup;
	}
	public SupOrderWork getSupOrderWork() {
		return supOrderWork;
	}
	public void setSupOrderWork(SupOrderWork supOrderWork) {
		this.supOrderWork = supOrderWork;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public double getPrice() {
		return price;
	}
	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}
	public void setPrice(double price) {
		this.price = price;
	}


	public SupplyItem getSupplyItem() {
		return supplyItem;
	}

	public void setSupplyItem(SupplyItem supplyItem) {
		this.supplyItem = supplyItem;
	}

}
