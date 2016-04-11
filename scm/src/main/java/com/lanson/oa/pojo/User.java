package com.lanson.oa.pojo;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private String lastName;
    private String workCode;
	private Dept dept;

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public int getId() {
		return id;
	}
	public String getLastName() {
		return lastName;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
}
