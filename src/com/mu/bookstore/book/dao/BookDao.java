package com.mu.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.category.dao.CategoryDao;
import com.mu.bookstore.category.domian.Category;

public class BookDao {
QueryRunner query=new TxQueryRunner();
CategoryDao cdao=new CategoryDao();
public List<Book> findAll() {
	
	String sql="select * from book where del=false";
	try {
		return query.query(sql, new BeanListHandler<Book>(Book.class));
	} catch (SQLException e) {
		System.out.println("！！！Book中的查询所有findall没有查询成功");
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	
	
}

public List<Book> findByCid(String cid) {
	
	String sql="select * from book where cid=? and del=false";
	try {
		return query.query(sql, new BeanListHandler<Book>(Book.class),cid);
	} catch (SQLException e) {
		System.out.println("！！！Book中的按分类查询findall没有查询成功");
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	
	
}

public Book findByBid(String parameter) {
	
	String sql="select * from book where bid=? and del=false";
	try {
		Map<String,Object> map=query.query(sql, new MapHandler(),parameter);
		Category cat=CommonUtils.toBean(map, Category.class);
		Book book=CommonUtils.toBean(map, Book.class);
		book.setCategory(cat);
		return book;
	} catch (SQLException e) {
		System.out.println("！！！Book中的按分类查询findall没有查询成功");
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	
	
}

public void add(Book book) {
	String sql="insert into book values(?,?,?,?,?,?,?)";
	Object[] par={book.getBid(),book.getBname(),book.getPrice(),book.getAuthor(),book.getImage(),book.getCid(),0};
try {
	query.update(sql, par);
} catch (SQLException e) {
	e.printStackTrace();
	throw new RuntimeException(e);
}
}

public void deleteByBid(String bid) {
	String sql="update book set del=true where bid=?";
	try {
		query.update(sql,bid);
	} catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
}

public void edit(Book book) {
	//String sql="update book set bname=? price=? author=? image=? cid=? where bid=?";
	String sql = "update book set bname=?, price=?,author=?, image=?, cid=? where bid=?";
	Object[] par={book.getBname(),book.getPrice(),
			book.getAuthor(),book.getImage(),
			book.getCid(),book.getBid()};
	try {
		query.update(sql,par);
	} catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	
}
}
