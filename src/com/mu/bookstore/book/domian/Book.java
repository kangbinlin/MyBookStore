package com.mu.bookstore.book.domian;

import com.mu.bookstore.category.domian.Category;

public class Book {
	private String bid;
	private String bname;
	private double price;
	private String author;
	private String image;
	private String cid;
	private Category category;
	public Book() {
		super();
		
	}
	public Book(String bid, String bname, double price, String author,
			String image, String cid, Category category) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.price = price;
		this.author = author;
		this.image = image;
		this.cid = cid;
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price
				+ ", author=" + author + ", image=" + image + ", cid=" + cid
				+ ", category=" + category + "]";
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	

}
