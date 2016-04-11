package com.lanson.oa.pojo.out;

import com.lanson.oa.pojo.OrderInfo;

/**
 * Created by fi25 on 15-10-16.
 * 出货记录
 */
public class OrderOut {

    public String getCon_number() {
        return con_number;
    }

    public void setCon_number(String con_number) {
        this.con_number = con_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public double getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(double outNumber) {
        this.outNumber = outNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int  id;
    private String  outDate;
    private int orderId;
    private double outNumber;
    private int userId;
    private String con_number;
    private double order_price;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private int year;
    private OrderInfo  orderInfo;


    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }


}
