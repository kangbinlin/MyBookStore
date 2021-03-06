package com.mu.bookstore.cart.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.mu.bookstore.book.domian.Book;

public class CartItem {
	private Book book;
	private int count;
	
	/*包含单条记录的总价计算*/
	public double getSubtotal()
	{
		BigDecimal d=BigDecimal.valueOf(book.getPrice());
		BigDecimal d1=BigDecimal.valueOf(count);
		return 	d.multiply(d1).doubleValue();
	}
	

	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + "]";
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
