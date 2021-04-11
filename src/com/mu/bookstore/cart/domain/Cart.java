package com.mu.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.mu.bookstore.book.domian.Book;

public class Cart {
	
	private Map<String,CartItem> map=new LinkedHashMap<String,CartItem>();
	//键是商品id
	/*包含对购物车内的条目的计算总价，
	 * 添加条目，清空，根据商品编号删除指定条目，获取所有条目*/
	
	public double getTotal()
	{
		BigDecimal sum=new BigDecimal("0");
		for(CartItem item:map.values())
		{
			BigDecimal subtotal=BigDecimal.valueOf(item.getSubtotal());
			
		sum=sum.add(subtotal);
	
		}
		return sum.doubleValue();
	}
	
	@Test
	public void fun()
	{
		CartItem item=new CartItem();
		Book book=new Book();
		book.setPrice(10);
		item.setBook(book);
		item.setCount(10);
//		System.out.println(item.getSubtotal());
		map.put("1", item);
		CartItem item2=map.get("1");
		getTotal();
		
		System.out.println(map.get("1"));
		System.out.println(item2.getSubtotal());
		System.out.println(getTotal());
	}

	/*根据传过来的cartitem添加到购物车中添加*/
	public void add(CartItem item)
	{
		String bid=item.getBook().getBid();//获得传进来的商品id
		//如果购物车集合中已经有这个商品了，就加一，没有就把
		if(map.containsKey(bid))
		{
			CartItem existItem=map.get(bid);
			existItem.setCount(existItem.getCount()+item.getCount());
			map.put(bid, existItem);
		}
		else
		{
			map.put(bid, item);
		}
			
	}
	public void clear()
	{
	map.clear();
			
	}
	public void delete(String bid)
	{
	map.remove(bid);
			
	}
	public Collection<CartItem> getCartItems()
	{
	return map.values();
			
	}
	
	
}
