package com.mu.bookstore.order.domain;

import java.math.BigDecimal;

import com.mu.bookstore.book.domian.Book;

public class OrderItem {
	private String iid;
	private  int count;
	private  Book book;
	private double subtotal;
	private Order order;
	public OrderItem() {
		super();
		
	}
	public OrderItem(String iid, int count, Book book, double subtotal,
			Order order) {
		super();
		this.iid = iid;
		this.count = count;
		this.book = book;
		this.subtotal = subtotal;
		this.order = order;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	

}
