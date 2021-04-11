package com.mu.bookstore.book.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.mu.bookstore.book.dao.BookDao;
import com.mu.bookstore.book.domian.Book;

public class BookService {
	BookDao dao=new BookDao();

	public List<Book> findAll() {
		
		
		return dao.findAll();
		
		
	}

	public List<Book> findByCid(String cid) {
		
		
		return dao.findByCid(cid);
		
		
	}

	public Book findByBid(String parameter) {
		
		
		return dao.findByBid(parameter);
		
		
	}

	public void add(Book book) {
		dao.add(book);
	}

	public void deleteByBid(String bid) {
		dao.deleteByBid(bid);
	}

	public void edit(Book book) {
		dao.edit(book);
	}
	
	public boolean changeSize(int newWidth, int newHeight, File file) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "jpg", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
