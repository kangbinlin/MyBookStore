package com.mu.bookstore.user.service;

import com.mu.bookstore.user.dao.UserDao;
import com.mu.bookstore.user.domain.User;


public class UserService {
	UserDao dao=new UserDao();
/*根据收到的userq调用dao的方法，如果用户名重复就抛异常
 * 如果email重复就抛异常
 * 如果没抛异常就插入*/
	public void regist(User userq)throws MyException {
		User user=dao.findByUsername(userq);
		if(user!=null)
		{throw new MyException("The username is already exist唉");}
		
		user=dao.findByEmail(userq);
		if(user!=null)
		{throw new MyException("The email is already exist唉");}
		 
		dao.add(userq);
	}
	public void findByCode(String code) throws Exception {
		User user=dao.findByCode(code);
		if(user==null)
		{throw new MyException("您还未注册，请注册后到邮箱点击正确的激活邮件");}
	if(user.isState())
	{throw new MyException("您曾经激活过，不要再次激活");}
	dao.changeState(user.getUid());
	}
	
	public User login(User userq) throws Exception {
		
		User userh=dao.findByUsername(userq);
		if(userh==null)
		{throw new MyException("您还没有注册");}
		if(!userq.getPassword().equals(userh.getPassword()))
		{throw new RuntimeException("您的密码错误");}
		if(!userh.isState())
		{throw new MyException("您还没有激活，请尽快激活");}
		
		return userh;
	}
}
