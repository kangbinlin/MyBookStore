package com.mu.bookstore.category.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.mu.bookstore.category.domian.Category;
import com.mu.bookstore.category.service.CategoryService;

public class AdminCategoryServlet extends BaseServlet {

	CategoryService cservice=new CategoryService();
	public String allCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("categoryList", cservice.allCategory());

		return "f:/adminjsps/admin/category/list.jsp";
	}

	
	public String addCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		Category cat=CommonUtils.toBean(request.getParameterMap(), Category.class);
		cat.setCid(CommonUtils.uuid());
		cservice.addCategory(cat);
		
		return allCategory(request,response);
	}
	public String delCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		try {
			cservice.delCategory(cid);
		} catch (Exception e) {
		request.setAttribute("msg", e.getMessage());
		return "f:/adminjsps/msg.jsp";
		}
		
		return allCategory(request,response);
	}
	public String preEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		request.setAttribute("category", cservice.findCategoryByCid(cid));
		return "f:/adminjsps/admin/category/mod.jsp";		
	}
	
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Category cat=CommonUtils.toBean(request.getParameterMap(), Category.class);
		cservice.edit(cat);
		
		return allCategory(request,response);
	}
}
