package com.mu.bookstore.cart.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.book.service.BookService;
import com.mu.bookstore.cart.domain.Cart;
import com.mu.bookstore.cart.domain.CartItem;

public class CartServlet extends BaseServlet {

	

	/**获得bid,获得数量，用这两个参数创建一个cartitem，
	 * 把这个item放到session中的cart中
	 * 转发到cart.list中*/
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
Cart cart=(Cart)request.getSession().getAttribute("cart");

String bid=request.getParameter("bid");
BookService service=new BookService();
Book book=service.findByBid(bid);
int count=Integer.parseInt(request.getParameter("count"));
CartItem item=new CartItem();

item.setBook(book);
item.setCount(count);

cart.add(item);
	return "f:/jsps/cart/list.jsp";	

		
	}

	
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		cart.clear();
		
		return "f:/jsps/cart/list.jsp";	
		
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		String bid=request.getParameter("bid");
		cart.delete(bid);
		
		return "f:/jsps/cart/list.jsp";	
		
	}


}
