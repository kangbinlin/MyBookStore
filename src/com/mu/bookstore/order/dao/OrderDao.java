package com.mu.bookstore.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.order.domain.Order;
import com.mu.bookstore.order.domain.OrderItem;

public class OrderDao {
	QueryRunner query=new TxQueryRunner();

	public void addOrder(Order order) {
		
		/*在数据库中添加order*/
		String sql="insert into orders values(?,?,?,?,?,?)";
		Object par[]={order.getOid(),order.getOrderTime(),order.getTotal(),
				order.getState(),order.getOwner().getUid(),order.getAddress()};
		try {
			query.update(sql, par);
		} catch (SQLException e) {
			System.out.println("OrderDao中插入订单失败");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void addOrderItems(List<OrderItem> items) {
		/*在数据库中添加orderItems*/
		/**
		 * QueryRunner类的batch(String sql, Object[][] params)
		 * 其中params是多个一维数组！
		 * 每个一维数组都与sql在一起执行一次，多个一维数组就执行多次
		 */
		String sql="insert into orderitem values(?,?,?,?,?)";
		Object[][]parm=new Object[items.size()][];
		for(int x=0;x<items.size();x++)
		{
			OrderItem item=items.get(x);
			parm[x]=new Object[]{item.getIid(),item.getCount(),item.getSubtotal(),item.getOrder().getOid(),item.getBook().getBid()};
		}
		
		try {
			query.batch( sql, parm);
		} catch (SQLException e) {
			System.out.println("！！！OrderDao插入orderitems没成功");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Order> findOrdersByUid(String uid) {
		/*遍历每一个order，
		 * 调用loadOrderBookItems遍历每一个order中的每个order中的item
		 * 返回orderlist*/
		String sql="select * from orders where uid=?";
		List<Order> orders=null;
		try {
			orders =query.query(sql,new BeanListHandler<Order>(Order.class), uid);
			for(Order order:orders)
			{
				loadOrderBookItem(order);
			}
		} catch (SQLException e) {
			System.out.println("！！！OrderDao中的查询我的订单失败");
			e.printStackTrace();
			throw new RuntimeException("！！！OrderDao中的查询我的订单失败");
		}
		
		
		return orders;
		
		
	}

	private void loadOrderBookItem(Order order) {
		/*loadOrderBookItem：遍历每个order中的orderitem
		 * 通过orderlist把数据库中多个order+book item遍历得到maplist
		 * 通过调用toOrderItemList把maplist转换成orderitem
		 * 把查询完毕的orderitems放到order中*/
		List<Map<String,Object>> mixitemList=new ArrayList<Map<String,Object>>();
		String sql="select * from orderitem oi,book b where oi.bid=b.bid and oid=?";
		try {
			mixitemList=query.query(sql, new MapListHandler(), order.getOid());
			List<OrderItem>orderitems=toOrderItemList(mixitemList);
			order.setOrderItems(orderitems);
			
			
		} catch (SQLException e) {
			System.out.println("！！！OrderDao中的查询我的订单之查询订单条目失败");
			e.printStackTrace();
			throw new RuntimeException("！！！OrderDao中的查询我的订单之查询订单条目失败");
		}
		
	}

	private List<OrderItem> toOrderItemList(
			List<Map<String, Object>> mixitemList) {
		/*toOrderItemList：
		 * 遍历得到的order+bookitem的maplist遍历循环用map调用toOrderItem
		 * 得到orderItemList（一个订单下的多尔item）
		 * 转化成orderItems返回给调用者
		 * */
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		for(Map<String, Object> mixitem:mixitemList)
		{
			
			OrderItem orderitem=toOrderItem(mixitem);
			orderItems.add(orderitem);
		}
		
		return orderItems;
		
		
	}

	private OrderItem toOrderItem(Map<String, Object> mixitem) {
		/*toOrderItem：把得到的order+bookitem的map
		 * 用得到的单条记录map分别转换成orderitem和book，把book放到orderitem中，
		 * 返回转换好的orderitem
		 * */
		OrderItem orderitem=CommonUtils.toBean(mixitem, OrderItem.class);
		Book book=CommonUtils.toBean(mixitem, Book.class);
		orderitem.setBook(book);
		
		return orderitem;
		
		
	}

	public Order findOrderByOid(String oid) {
		
		String sql="select * from orders where oid=?";
		
		try {
			Order order=query.query(sql,new BeanHandler<Order>(Order.class),oid);
			loadOrderBookItem(order);
			return order;
		} catch (SQLException e) {
			System.out.println("！！！OrderDao中的按oid查询失败");
			e.printStackTrace();
			throw new RuntimeException(e+"！！！OrderDao中的按oid查询失败");
		}
		
		
	}

	public void updateState(String oid, int i) {
		String sql="update orders set state=?  where oid=?";
		try {
			query.update(sql,i,oid);
		} catch (SQLException e) {
			System.out.println("！！！OrderDao中修改状态失败");
			e.printStackTrace();
			throw new RuntimeException(e+"！！！OrderDao中修改状态失败");
		}
	}

	public int findStateByOid(String oid) {
		String sql="select state from orders where oid=?";
		
		try {
			return (Integer)query.query(sql, new ScalarHandler(),oid);
		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e+"！！！Orderdao中的通过oid寻找state失败啦");
		}
		
		
	}

	public void changeState(String r6_Order, int i) {
		String sql="update orders set state=? where oid=?";
		try {
			query.update(sql,i,r6_Order);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}

	


}
