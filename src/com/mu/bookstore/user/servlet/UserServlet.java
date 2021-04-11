package com.mu.bookstore.user.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.mu.bookstore.cart.domain.Cart;
import com.mu.bookstore.user.domain.User;
import com.mu.bookstore.user.service.MyException;
import com.mu.bookstore.user.service.UserService;

public class UserServlet extends BaseServlet {

	/*regist：
	 * 将userq封装
	 * 将提交的userq补全uid和激活码,校验输入
	 * 提交给service，如果service逻辑判断抛出异常，
	 * 就在msg中传入错误信息并把mag和userq放入request域中，转发回Regist.jsp
	 * 如果service没有抛出异常，做个发邮件方法，调用发邮件
	 * 将注册成功msg传入request域中转发回msg中
	 * 
	 * */
	UserService service=new UserService();
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
User userq=CommonUtils.toBean(request.getParameterMap(), User.class);
userq.setUid(CommonUtils.uuid());
userq.setCode(CommonUtils.uuid());
Map<String, String>errors=new HashMap<String, String>();
String username=userq.getUsername();
String password =userq.getPassword();
String email=userq.getEmail();
if(username.trim().isEmpty()||username==null)
{errors.put("username", "username can't be empty唉");}
else if(username.length()<3||username.length()>10){
	errors.put("username", "username length can't less 3 or more than 10唉");
}
if(password.trim().isEmpty()||password==null)
{errors.put("password", "password can't be empty唉");}
else if(password.length()<3||password.length()>10){
	errors.put("password", "password length can't less 3 or more than 10唉");
}
if(email==null||email.trim().isEmpty())
{errors.put("email", "email can't be empty唉");
}
else if(!email.matches("\\w+@\\w+\\.\\w+"))
{errors.put("email", "email's format is wrong唉");}
if(errors.size()>0)
{
request.setAttribute("userq", userq);
request.setAttribute("errors", errors);
return "f:/jsps/user/regist.jsp";

}

try {
	service.regist(userq);
} catch (MyException e) {
	request.setAttribute("userq", userq);
	request.setAttribute("msg", e.getMessage());
	return "f:/jsps/user/regist.jsp";
}
sendEmail(userq);
request.setAttribute("msg", "you have successfully registered成功,请去邮箱激活账户");
return "f:/jsps/msg.jsp";

}
/*发送邮件需要发件人主机地址host，发件人用户名uname，发件人密码password，
 * 发件人邮箱地址from，发件主题subject，发件内容content（要把其中的code替换掉）
 * 创建一个.properties的配置文件，在里面配置好*/

	@Test
	public void fun()
	{
		User u=new User();
		u.setEmail("kbl130991@126.com");
		u.setCode(CommonUtils.uuid());
		sendEmail(u);
	}
	
	
	public void sendEmail(User userq){
		/*把配置文件中的信息获取到properties集合中
		 * 获取到每一项，用messagefor麻仁把content中的{0}替换掉
		 * */
		Properties ji=new Properties();
		try {
			ji.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			System.out.println("servlet中配置文件没有加载进来");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String emailHost=ji.getProperty("host");
		String emailUser=ji.getProperty("uname");
		String pwd=ji.getProperty("pwd");
		String emailAuth=ji.getProperty("auth");
		String emailProtocol=ji.getProperty("protocol");
		
		String subject=ji.getProperty("subject");
		String content=ji.getProperty("content");
		content=MessageFormat.format(content, userq.getCode());
		System.out.println(userq.getCode());
		String to=userq.getEmail();
		//content="lalala";
		System.out.println(emailProtocol+" "+emailHost+" "+emailUser+" "+pwd+" "+emailAuth+" "+subject+" "+content);
		System.out.println(content);
		
			
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.host",emailHost);
			props.setProperty("mail.smtp.auth",emailAuth);
			props.setProperty("mail.transport.protocol",emailProtocol);
			Session ses=Session.getInstance(props);
			//ses.setDebug(true);
			MimeMessage mess=new MimeMessage(ses);
			mess.setFrom(new InternetAddress(emailUser));
			
			mess.setSubject(subject);
			mess.setContent(content, "text/html;charset=utf-8");
			Transport tran=ses.getTransport();
			tran.connect(emailHost, emailUser, pwd);
			tran.sendMessage(mess, new Address[]{new InternetAddress(to)});
			tran.close();
		}  catch (Exception e) {
			System.out.println("servlet中邮件没发出去");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
			
	}
	
	/*激活功能
	 * 获取激活码，把激活码传给service，如果人家没查到就收到异常，就保存异常信息msg到request域中并转发回msg.jsp
	 * 如果没抛异常保存激活成功信息msg并转发回msg.jsp
	 * 
	 * */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String code=request.getParameter("code");
		try {
			service.findByCode(code);
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
		}
		request.setAttribute("msg","恭喜您！激活成功！！您现在可以登陆了");
		return "f:/jsps/msg.jsp";
		  
	}
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*封装前端数据为user，校验，传给service，抓住异常就存错误msg，转发回登录页面
		 * 如果无异常，保存查到的用户信息到session域中
		 * request.getSession().setAttribute("userd", userh);
		 * */
		
		
		User user=CommonUtils.toBean(request.getParameterMap(), User.class);
		String username=user.getUsername();
		String password=user.getPassword();
		Map<String, String>errors=new HashMap<String, String>();
		if(username.trim().isEmpty()||username==null)
		{errors.put("username", "username can't be empty唉");}
		else if(username.length()<3||username.length()>10){
			errors.put("username", "username length can't less 3 or more than 10唉");
		}
		if(password.trim().isEmpty()||password==null)
		{errors.put("password", "password can't be empty唉");}
		else if(password.length()<3||password.length()>10){
			errors.put("password", "password length can't less 3 or more than 10唉");
		}
		if(errors.size()>0)
		{
		request.setAttribute("userq", user);
		request.setAttribute("errors", errors);}
		
		try {
			User userh=service.login(user);
			request.getSession().setAttribute("userd", userh);
			request.getSession().setAttribute("cart", new Cart());
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("userq", user);
			return "f:/jsps/user/login.jsp";
		}
		
		return "r:/index.jsp";
	}	
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
		  
	}
}
