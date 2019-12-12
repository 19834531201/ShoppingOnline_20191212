package nuc.sw.shopping.service;

import nuc.sw.shopping.dao.*;
import nuc.sw.shopping.entity.Book;
import nuc.sw.shopping.entity.ShoppingCart;
import nuc.sw.shopping.entity.User;

import java.util.Scanner;
import nuc.sw.shopping.exception.*;

public class BookServiceImpl implements BookService {
    private BookDaoImpl dao;
  
    public BookServiceImpl() {
		// TODO Auto-generated constructor stub
    	dao = new BookDaoImpl();
    }
	@Override
	public Book[] queryBooks() {
		// TODO Auto-generated method stub
		Book[] books = (Book[])dao.queryBooks().toArray();
		return books;
	}

	@Override
	public Book queryById(String id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}
	public Book queryByName(String name) {
		return dao.queryByName(name);
	}
	
	
	//����鼮,ֻ����ӵ��µ�������飬û���漰�����������������
	public static void addBook(Book book) {
		BookDaoImpl.writeFile(book);
	}

}
