package com.mu.bookstore.order.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import com.mu.bookstore.cart.domain.Cart;
import com.mu.bookstore.cart.domain.CartItem;
import com.mu.bookstore.order.domain.Order;
import com.mu.bookstore.order.domain.OrderItem;
import com.mu.bookstore.order.service.OrderException;
import com.mu.bookstore.order.service.OrderService;
import com.mu.bookstore.user.domain.User;

public class OrderServlet extends BaseServlet {

OrderService service=new OrderService();
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*拿到session中的cart
		 * 新建orderitem和order用cart生成orderitem，生成order，try调用service方法，没抛异常就保存成功信息
		 * 转发回jsps/order/desc.jsp*/
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		System.out.println(cart);
		Order order=new Order();
		
		List<OrderItem> olist=new ArrayList<OrderItem>();
order.setOid(CommonUtils.uuid());
		
		order.setOrderTime(new Date());
		User owner=(User)request.getSession().getAttribute("userd");
		order.setOwner(owner);
		order.setState(1);
		order.setTotal(cart.getTotal());
		
		for(CartItem citem:cart.getCartItems())
		{
			OrderItem oitem=new OrderItem();
			//对象要建在里面，每一个cartitem就要创建一个相应的新orderitem，这个uuid工具在生成的时候如果是同一个对象就会生成同一uuid	
		oitem.setIid(CommonUtils.uuid());

		oitem.setBook(citem.getBook());
		oitem.setCount(citem.getCount());
	oitem.setSubtotal(citem.getSubtotal());
	oitem.setOrder(order);
	
	olist.add(oitem);
	
		
		}
		order.setOrderItems(olist);
		
		
			service.add(order);
		cart.clear();
		request.setAttribute("order", order);
		System.out.println(cart);
		System.out.println(order);
		System.out.println(olist);
		
		return "f:/jsps/order/desc.jsp";
//return null;
	}

	public String myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*获得session中的user，获取uid，
		 * 用uid调用service中findOrdersByUid获得orders的list
		 * 保存到request中
		 * 转发到jsps/order/list.jsp，遍历显示我的订单
		 * */
		
		User user=(User)request.getSession().getAttribute("userd");
		
		List<Order> orders=service.findOrdersByUid(user.getUid());
		request.setAttribute("orders", orders);
		
		return "f:/jsps/order/list.jsp";
		//return null;
		
	}
	
	public String loadOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	String oid=request.getParameter("oid");
	Order order=service.findOrderByOid(oid);
	request.setAttribute("order", order);	
		return "f:/jsps/order/desc.jsp";
	}
	
	
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid=request.getParameter("oid");
		System.out.println(oid);
		try {
			service.setState(oid);
			request.setAttribute("msg", "确认收货成功");
		} catch (OrderException e) {
			// TODO: handle exception
			request.setAttribute("msg",e.getMessage());
		}
		
		return"f:/jsps/msg.jsp";
	}
	
	public void zhifu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		/*用一分钱测试付款，先设置好十三个参数，其中MerId、Url从配置文件中获取
		 * 从配置文件获取keyvalue，用这些参数调用paymentutils的buildhmac工具，
		 * 从配置文件获取易宝支付网址，在后面拼接好13+1参数
		 * 重定向到此网址
		 * */
		Properties pro=new Properties();
		InputStream input=this.getClass().getClassLoader().getResourceAsStream("merchantInfo.properties");
		pro.load(input);
		String p0_Cmd="Buy";
		String p1_MerId=pro.getProperty("p1_MerId");
		String p2_Order=request.getParameter("oid");
		String p3_Amt="0.01";
		String p4_Cur="CNY";
		String p5_Pid="";
		String p6_Pcat="";
		String p7_Pdesc="";
		String p8_Url=pro.getProperty("p8_Url");
		String p9_SAF="";
		String pa_MP="";
		String pd_FrpId=request.getParameter("pd_FrpId");
		String pr_NeedResponse="1";
		
		String keyValue=pro.getProperty("keyValue");
		String hamc=PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		 
		StringBuilder sb=new StringBuilder(pro.getProperty("url"));
		sb.append("?p0_Cmd=").append(p0_Cmd);
		sb.append("&p1_MerId=").append(p1_MerId);
		sb.append("&p2_Order=").append(p2_Order);
		sb.append("&p3_Amt=").append(p3_Amt);
		sb.append("&p4_Cur=").append(p4_Cur);
		sb.append("&p5_Pid=").append(p5_Pid);
		sb.append("&p6_Pcat=").append(p6_Pcat);
		sb.append("&p7_Pdesc=").append(p7_Pdesc);
		sb.append("&p8_Url=").append(p8_Url);
		sb.append("&p9_SAF=").append(p9_SAF);
		sb.append("&pa_MP=").append(pa_MP);
		sb.append("&pd_FrpId=").append(pd_FrpId);
		sb.append("&pr_NeedResponse=").append(pr_NeedResponse);
		sb.append("&hamc=").append(hamc);
		
		response.sendRedirect(sb.toString());
	
		
	}
	public String back(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
	/*获得易宝支付请求流携带的11个参数
	 * 从配置文件获取keyvalue，用这些参数和PaymentUtil的verifyCallback方法进行验证
	 * 如果成功说明信息未被修改，如果失败就在request域中存入msg信息，转发至msg.jsp
	 * 验证成功，调用service修改付款状态
	 * 如果r9_BType.equals("2")就给他响应success，让它不要一直访问了
	 * 在request域中存入成功信息，转发
	 * */
		
	String hmac=request.getParameter("hmac");
	String p1_MerId=request.getParameter("p1_MerId");
	String r0_Cmd=request.getParameter("r0_Cmd");
	String r1_Code=request.getParameter("r1_Code");
	String r2_TrxId=request.getParameter("r2_TrxId");
	String r3_Amt=request.getParameter("r3_Amt");
	String r4_Cur=request.getParameter("r4_Cur");
	String r5_Pid=request.getParameter("r5_Pid");
	String r6_Order=request.getParameter("r6_Order");
	String r7_Uid=request.getParameter("r7_Uid");
	String r8_MP=request.getParameter("r8_MP");
	String r9_BType=request.getParameter("r9_BType");
	
	Properties pro=new Properties();
	InputStream input=this.getClass().getClassLoader().getResourceAsStream("merchantInfo.properties");
	pro.load(input);
	
	String keyValue=pro.getProperty("keyValue");
	boolean boo=PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
	if(!boo)
	{request.setAttribute("msg", "支付失败，你不是啥好人");
	return"f:/jsps/msg.jsp";
	}
	service.pay(r6_Order);
	if(r9_BType.equals("2"))
	{response.getWriter().print("success");}
	
	request.setAttribute("msg", "支付成功,请等待确认收货");
	return"f:/jsps/msg.jsp";
	}
}
