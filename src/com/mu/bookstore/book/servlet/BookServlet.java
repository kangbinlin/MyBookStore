package com.mu.bookstore.book.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.book.service.BookService;

public class BookServlet extends BaseServlet {

	private BookService service=new BookService();
	 
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Book> list=new ArrayList<Book>();
		list=service.findAll();
		request.setAttribute("BookList", list);
		return "f:/jsps/book/list.jsp";
	}
	public String findByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Book> list=new ArrayList<Book>();
		list=service.findByCid(request.getParameter("cid"));
		request.setAttribute("BookList", list);
//		System.out.println(list);
		return "f:/jsps/book/list.jsp";

	}
	public String findByBid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("Book", service.findByBid(request.getParameter("bid")));
		return "f:/jsps/book/desc.jsp";
	}
}
