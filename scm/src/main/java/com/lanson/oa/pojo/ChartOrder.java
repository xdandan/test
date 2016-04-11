package com.lanson.oa.pojo;

public class ChartOrder {
	
	private  int supplyId;
	private  int  month;
	private  int year;


	private float number;
	private  float total;
	public int getMonth() {
		return month;
	}
	public float getTotal() {
		return total;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setTotal(float total) {
		this.total = total;
	}


	private float one;
	private float two;
	private float three;
	private float four;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private  String name ;

	public float getOne() {
		return one;
	}
	public float getTwo() {
		return two;
	}
	public float getThree() {
		return three;
	}
	public float getFour() {
		return four;
	}
	public void setOne(float one) {
		this.one = one;
	}
	public void setTwo(float two) {
		this.two = two;
	}
	public void setThree(float three) {
		this.three = three;
	}
	public void setFour(float four) {
		this.four = four;
	}
	public int getSupplyId() {
		return supplyId;
	}
	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}


	public float getNumber() {
		return number;
	}

	public void setNumber(float number) {
		this.number = number;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
