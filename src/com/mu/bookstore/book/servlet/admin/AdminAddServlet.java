package com.mu.bookstore.book.servlet.admin;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;

import com.mu.bookstore.book.domian.Book;
import com.mu.bookstore.book.service.BookService;
import com.mu.bookstore.category.domian.Category;

public class AdminAddServlet extends HttpServlet {
BookService bservice=new BookService();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");//处理响应编码
		request.setCharacterEncoding("UTF-8");
		
	System.out.println(request.getParameter("bname"));
	/*用diskfileitemfactory创建工厂
	 * 用工厂新建Servlet上传器Servletfileupload
	 * 设置上传器限制大小
	 * 用上传器的parseRequest方法解析request流带来的上传文件（获得fileitem的list）
	 * 遍历fileitem放入map中
	 * 把map转换为Book对象，并为book设置bid
	 * 把map转换为category对象
	 * 为book设置category对象
	 *设置文件名
	 * 限制文件名如果不是以图片格式结尾就设置错误信息，转发到addpre方法上
	 * 设置存储路径
	 * 创建文件，并保存fileitem（1）的图片到文件中（用write方法）
	 * 设置book中的图片路径
	 * 调用service的add方法保存到数据库中
	 * 保存好后对图片进行修改（image方法）
	 * 转发到"/admin/AdminBookServlet?method=findAll"
	 * */
	try {
		DiskFileItemFactory fa=new DiskFileItemFactory();
		ServletFileUpload up=new ServletFileUpload(fa);
		up.setHeaderEncoding("UTF-8");

	up.setFileSizeMax(50*1024);

	List<FileItem> items=new ArrayList<FileItem>();
	

				
try {
	items= up.parseRequest(request);
//	System.out.println(items.get(0));
//	System.out.println(items);
} catch (Exception e) {
	throw new RuntimeException("文件大小超过50kb");
}	
		
		
		
		Map<String,String>map=new HashMap<String,String>();
		for(FileItem item:items)
		{map.put(item.getFieldName(),item.getString());}
		Book book=CommonUtils.toBean(map, Book.class);
		book.setBid(CommonUtils.uuid());
		
		
		Category cat=CommonUtils.toBean(map, Category.class);
		book.setCategory(cat);
		String fname=CommonUtils.uuid()+"_"+items.get(1).getName();
		if(!fname.toLowerCase().endsWith("jpg"))
		{
		request.setAttribute("msg", "您上传的图片不是jpg格式的，请重新上传");
		request.getRequestDispatcher("/admin/AdminServlet?method=addPre").forward(request, response);	
		return;
		}
		String path=this.getServletContext().getRealPath("/book_img");
		
		/** 创建文件，并保存fileitem（1）的图片到文件中（用write方法）
	 * 设置book中的图片路径
	 * 调用service的add方法保存到数据库中
	 * 保存好后对图片进行校验（image方法）
	 * 转发到"/admin/AdminBookServlet?method=findAll"*/
		File file=new File(path,fname);
		items.get(1).write(file);
		
		book.setImage("book_img/"+fname);
		bservice.add(book);
		
		bservice.changeSize(106, 150, file);
		//把上传的图片转换成指定大小
		
		request.getRequestDispatcher("/admin/AdminServlet?method=findAll").forward(request, response);	
		
		
	} catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	 
	
	}

	

}
