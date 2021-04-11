package com.mu.bookstore.category.service;

import java.util.ArrayList;
import java.util.List;

import com.mu.bookstore.book.dao.BookDao;
import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.category.dao.CategoryDao;
import com.mu.bookstore.category.domian.Category;

public class CategoryService {
CategoryDao dao=new CategoryDao();
BookDao bdao =new BookDao();
	public List<Category> findAll() {
		
		
		return dao.findAll();
		
		
	}
	public List<Category> allCategory() {
		
		
		return dao.findAll();
		
		
	}
	public void addCategory(Category cat) {
		dao.addCategory( cat);
	}
	public void delCategory(String cid) throws CategoryException {
		//int coun=bdao.getCountByCid(cid);
		List<Book> book=new ArrayList<Book>();
		book=bdao.findByCid(cid);
		if(!book.isEmpty()||book.size()>0)
		{
			throw new CategoryException("该分类下还有图书，无法删除，请先转移图书分类，再进行删除");
		}
				
		dao.delCategory(cid);
	}
	public Category findCategoryByCid(String cid) {
		
		return dao.findCategoryByCid(cid);
		
		
	}
	public void edit(Category cat) {
	dao.edit(cat);
	}

}
