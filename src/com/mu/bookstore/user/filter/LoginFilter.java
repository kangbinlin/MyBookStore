package com.mu.bookstore.user.filter;


import java.io.IOException;
import java.net.HttpRetryException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mu.bookstore.user.domain.User;


public class LoginFilter implements Filter {

	@Override
	public void destroy() {
	}
	/*
	 * 1. 从session中获取用户信息
	 * 2. 判断如果session中存在用户信息，放行！
	 * 3. 否则，保存错误信息，转发到login.jsp显示
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
	
	HttpServletRequest request=(HttpServletRequest)arg0;
	User user=(User) request.getSession().getAttribute("userd");
	
	if(user!=null)
		{arg2.doFilter(arg0, arg1);}
	else{
	request.setAttribute("msg", "您还没有登陆！！");
	request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, arg1);
	}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
