package com.mu.bookstore.user.servlet;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class mm {
	@Test
	public  void sendMail() {
		try {
		String emailUser ="kbl130991@163.com";
		String emailPwd="XGMAQPWBQMFFQHTX";
		String emailHost ="smtp.163.com";
		String emailAuth ="true";
		String emailProtocol ="smtp";
		int emailPort =25;//端口不设置也行
		//获取系统环境信息
		//Properties props = System.getProperties();
		Properties props = new Properties();//用普通属性集合也行
		
		//设置邮件服务器
		props.setProperty("mail.smtp.host", emailHost); 
		//设置密码认证
		props.setProperty("mail.smtp.auth", emailAuth);
		//设置传输协议
		props.setProperty("mail.transport.protocol", emailProtocol);
		//创建session对象
		Session session = Session.getInstance(props);
		//设置输出日志
		session.setDebug(true);
		
		//邮件发送对象
		MimeMessage message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress(emailUser));
		//设置邮件主题
		message.setSubject("测试邮件");
		message.setContent("测试邮件啦啦啦4","text/html;charset=utf-8");
		//获取邮件发送管道
		Transport transport=session.getTransport();
		//连接管道
		//transport.connect(emailHost,emailPort, emailUser, emailPwd);
		transport.connect(emailHost, emailUser, emailPwd);
	
		//发送邮件
		transport.sendMessage(message,new Address[]{new InternetAddress("kbl130991@126.com")});
		//关闭管道
		transport.close();
		
		} catch (Exception e) {			
			e.printStackTrace();			
		}	
	}
}
