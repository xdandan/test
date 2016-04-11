package com.lanson.oa.pojo;

import java.io.Serializable;
import java.util.List;

public class SupOrderWork implements Serializable{

	private int id;
	private int  orderId;
	private int count;
	private String supOrderNo;
	private  String supDate;
	private  String bl;//板料
	private String js;//金属
	private  String quan;//圈
	private String bi;//脾
	private String dj;//钉铰
	private String wf;//外发
	private String pg;//抛光
	private double  packNumber;
	private double outNumber;
	private  double oweNumber;
	private String supRemark;
	
	private  String  supplyComment;
	private String   ourComment;
	
	private  String replyDate1;//上次复期
	private String replyDate2;//当前复期
	private String  curOutDate;//实际出货日期，出货数量》=90%以上
	
	

	
	
	public int getId() {
		return id;
	}

	public String getBl() {
		return bl;
	}
	public String getJs() {
		return js;
	}
	public String getQuan() {
		return quan;
	}
	public String getBi() {
		return bi;
	}
	public String getDj() {
		return dj;
	}
	public String getWf() {
		return wf;
	}
	public double getPackNumber() {
		return packNumber;
	}
	public double getOutNumber() {
		return outNumber;
	}
	public double getOweNumber() {
		return oweNumber;
	}
	public void setId(int id) {
		this.id = id;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}
	public void setJs(String js) {
		this.js = js;
	}
	public void setQuan(String quan) {
		this.quan = quan;
	}
	public void setBi(String bi) {
		this.bi = bi;
	}
	public void setDj(String dj) {
		this.dj = dj;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setWf(String wf) {
		this.wf = wf;
	}
	public void setPackNumber(double packNumber) {
		this.packNumber = packNumber;
	}
	public void setOutNumber(double outNumber) {
		this.outNumber = outNumber;
	}
	public void setOweNumber(double oweNumber) {
		this.oweNumber = oweNumber;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getPg() {
		return pg;
	}
	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getSupOrderNo() {
		return supOrderNo;
	}

	public String getSupDate() {
		return supDate;
	}

	public void setSupOrderNo(String supOrderNo) {
		this.supOrderNo = supOrderNo;
	}

	public void setSupDate(String supDate) {
		this.supDate = supDate;
	}

	public String getSupRemark() {
		return supRemark;
	}

	public void setSupRemark(String supRemark) {
		this.supRemark = supRemark;
	}

	public String getSupplyComment() {
		return supplyComment;
	}

	public String getOurComment() {
		return ourComment;
	}

	public void setSupplyComment(String supplyComment) {
		this.supplyComment = supplyComment;
	}

	public void setOurComment(String ourComment) {
		this.ourComment = ourComment;
	}


	public String getReplyDate1() {
		return replyDate1;
	}

	public String getReplyDate2() {
		return replyDate2;
	}

	public String getCurOutDate() {
		return curOutDate;
	}

	public void setReplyDate1(String replyDate1) {
		this.replyDate1 = replyDate1;
	}

	public void setReplyDate2(String replyDate2) {
		this.replyDate2 = replyDate2;
	}

	public void setCurOutDate(String curOutDate) {
		this.curOutDate = curOutDate;
	}
	
	

}
