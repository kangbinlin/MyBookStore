package com.mu.bookstore.category.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mu.bookstore.category.service.CategoryService;

import cn.itcast.servlet.BaseServlet;

public class CategoryServlet extends BaseServlet {
private CategoryService service=new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	request.setAttribute("categoryl", service.findAll());

	return "f:/jsps/left.jsp";

		
	}


}
