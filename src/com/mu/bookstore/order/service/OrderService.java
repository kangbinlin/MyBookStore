package com.mu.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.jdbc.JdbcUtils;

import com.mu.bookstore.order.dao.OrderDao;
import com.mu.bookstore.order.domain.Order;

public class OrderService {
	OrderDao dao=new OrderDao();

	
	public void add(Order order) {
		
		/*业务逻辑层，用事务
		 * 在数据库中写入订单或订单数据必须是事务，不然会造成数据丢失的情况*/
		try {
			JdbcUtils.beginTransaction();
			dao.addOrder(order);
			dao.addOrderItems(order.getOrderItems());
			
			JdbcUtils.commitTransaction();
			
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				System.out.println("!!!orderdao中创建/提交事务失败");
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
			System.out.println("!!!orderdao中回滚事务失败");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public List<Order> findOrdersByUid(String uid) {
		
		
		return dao.findOrdersByUid(uid);
		
		
	}

	public Order findOrderByOid(String oid) {
		
		
		return dao.findOrderByOid(oid);
		
		
	}

	public void setState(String oid) throws OrderException{
		//Order order=dao.findOrderByOid(oid);
		int state=dao.findStateByOid(oid);
		if(state!=3)
		{throw new OrderException("确认收货失败");}
		dao.updateState(oid,4);
	}

	public void pay(String r6_Order) {
	/*根据传过来的订单号获取付款状态，如果状态为1就改为2*/
		
		int state=dao.findStateByOid(r6_Order);
		if(state==1)
		{dao.changeState(r6_Order,2);}
	}

}
