package com.mu.bookstore.book.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.book.service.BookService;
import com.mu.bookstore.category.domian.Category;
import com.mu.bookstore.category.service.CategoryService;

public class AdminServlet extends BaseServlet{

	BookService service=new BookService();
	CategoryService cservice=new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
request.setAttribute("bookList", service.findAll());
		
	return"f:/adminjsps/admin/book/list.jsp";
	}

	
	public String findBookByBid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
	Book book=service.findByBid(bid);
	List<Category> list=cservice.findAll();
		request.setAttribute("book",book );


request.setAttribute("categoryList",list );
		return"f:/adminjsps/admin/book/desc.jsp";
	}
	
	
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("categoryList", cservice.findAll());
		
		return"f:/adminjsps/admin/book/add.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid=request.getParameter("bid");
		service.deleteByBid(bid);
		return findAll(request,response);
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*String price=request.getParameter("price");
		price=price.substring(0, price.length()-1);*/
		Book book=CommonUtils.toBean(request.getParameterMap(), Book.class);
	//book.setPrice(Integer.parseInt(price));
		service.edit(book);		
		return findAll(request,response);
	}
	public String lala(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("bname"));
		return null;
	}
}
