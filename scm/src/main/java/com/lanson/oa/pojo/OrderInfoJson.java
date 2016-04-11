package com.lanson.oa.pojo;

import java.io.Serializable;
import java.util.List;


public class OrderInfoJson implements Serializable{
	private List root;
	private  int count;

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
