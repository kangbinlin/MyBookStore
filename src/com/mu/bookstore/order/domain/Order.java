package com.mu.bookstore.order.domain;

import java.util.Date;
import java.util.List;

import com.mu.bookstore.user.domain.User;

public class Order {
	private String oid;
	private Date orderTime;
	private List<OrderItem> orderItems;
	private double total;// 合计
	private int state;// 订单状态有四种：1未付款 2已付款但未发货 3已发货但未确认收货 4已确认交易成功
	private User owner;// 订单所有者
	private String address;
	public Order() {
		super();
		
	}
	public Order(String oid, Date orderTime, List<OrderItem> orderItems,
			double total, int state, User owner, String address) {
		super();
		this.oid = oid;
		this.orderTime = orderTime;
		this.orderItems = orderItems;
		this.total = total;
		this.state = state;
		this.owner = owner;
		this.address = address;
	}
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
