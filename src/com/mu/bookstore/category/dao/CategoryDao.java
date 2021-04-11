package com.mu.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;

import com.mu.bookstore.category.domian.Category;

public class CategoryDao {
private QueryRunner query=new TxQueryRunner();
	public List<Category> findAll() {
		
		String sql="select * from category";
		try {
			return query.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			System.out.println("！！！category的dao中查询失败");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
				

		
		
	}
	
	public void addCategory(Category cat) {
		String sql="insert into category values(?,?)";
		try {
			query.update(sql,cat.getCid(),cat.getCname());
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public void delCategory(String cid) {
		String sql="delete from category where cid=?";
		try {
			query.update(sql,cid);
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public Category findCategoryByCid(String cid) {
		
		String sql="select * from category where cid=?";
		
		try {
			return query.query(sql, new BeanHandler<Category>(Category.class),cid);
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public void edit(Category cat) {
		String sql="update category set cname=? where cid=?";
		try {
			query.update(sql,cat.getCname(),cat.getCid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
