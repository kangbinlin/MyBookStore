package com.mu.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import cn.itcast.jdbc.TxQueryRunner;

import com.mu.bookstore.user.domain.User;

public class UserDao {
	QueryRunner query=new TxQueryRunner();
/**/
	@Test
	public void fun()
	{
	User er=new User();
	er.setUsername("lalala");
	User uu=findByUsername(er);
	System.out.println(uu);
	}
	
	public User findByUsername(User userq) {
		
		try {
			String user=userq.getUsername();
			String sql="select * from tb_user where username=?";
			return query.query(sql, new BeanHandler<User>(User.class),user );
		} catch (SQLException e) {
			System.out.println("userdao中的findByUsername查询错误");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		
	}

	public User findByEmail(User userq) {
		String sql="select * from tb_user where email=?";
		try {
			return query.query(sql, new BeanHandler<User>(User.class), userq.getEmail());
		} catch (SQLException e) {
			System.out.println("userdao中的findByEmail查询错误");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
	}

	public void add(User userq) {
		String sql="insert into tb_user value(?,?,?,?,?,?)";
		Object [] par={userq.getUid(),userq.getUsername(),userq.getPassword(),userq.getEmail(),userq.getCode(),userq.isState()};
	try {
		query.update(sql, par);
	} catch (SQLException e) {
		System.out.println("userdao插入失败");
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	}

	public User findByCode(String code) {
		 String sql="select * from tb_user where code=?";
		 try {
				return query.query(sql, new BeanHandler<User>(User.class),code);
			} catch (SQLException e) {
				System.out.println("userdao根据激活码查询失败");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		
	
		
		
	}

	public void changeState(String uid) {
		try {
			String sql="update tb_user set state=? where uid=?";
			query.update(sql,true,uid);
		} catch (SQLException e) {
			System.out.println("userdao根据激活码状态修改失败");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	

}
