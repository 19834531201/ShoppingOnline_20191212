package nuc.sw.shopping.entity;

import java.util.*;
import nuc.sw.shopping.exception.*;

public class ShoppingCart extends HashMap<Book,Integer> {


	public ShoppingCart() {
		// TODO Auto-generated constructor stub
		super();

	}
	
	/**
	 * ���鼮��ӽ����ﳵ,quantityΪ�仯�������
	 */
	public void add(Book book,int quantity) throws BookException{
		int number = 0;
		if(quantity > book.getNumber()) {
			throw new BookException(book.getName(), book.getNumber());			
		} else if(searchBook(book) == 0){
			this.put(book, quantity);
		} else if(searchBook(book) == 1) {
			for(Map.Entry<Book, Integer> map : this.entrySet()) {
				if(book.getName().equals(map.getKey().getName()))  {
					number = map.getValue();
					this.remove(book);
					this.put(book, quantity);	
					break;
				}
			}
		}
	}
	
	/**
	 * ���ٹ��ﳵ�е��鼮��ר�����ﳵ�Ľ�����
	 */
	public void cut(Book book) {
		int number = 0; 
		for(Map.Entry<Book, Integer> map : this.entrySet()) {
			if(book.getName().equals(map.getKey().getName()))  {
				number = map.getValue();
				this.remove(book);
				this.put(book, number - 1);	
				break;
			}
		}
	}
	
	/**
	 * ѡ���ﳵ����鼮���й������ﻹ�����⣬�޷����й�����Ҫ�޸Ĵ���
	 * �����漰�� 1.�û��ļ�����(�Ȱѹ��ﳵ�����º�ֱ�ӽ���coverFile(),��User��������) 2.�û����ﳵ�ĸ��� 3.����������������Ҫһ���ļ�ȥ����û��͹������Ʒ������
	 * 4.�׳��쳣ʱ��������
	 */
	public void buy(Book book,int quantity) throws BookException{
//		int number = 0;
		if(quantity > book.getNumber()) {
			throw new BookException(book.getName(), book.getNumber());
		}  if(searchBook(book) == 1) {
			for(Map.Entry<Book, Integer> map : this.entrySet()) {
				if(book.getName().equals(map.getKey().getName()))  {
//					number = map.getValue();
					this.remove(book);	
					break;
				}
			}
		}
		             
	}

	//�鿴���ﳵ���Ƿ���������,û�з���0���з���1
	public int searchBook(Book book) {
		int flag = 0;
		for(Book b :this.keySet()) {
			if(book.getName().equals(b.getName())) {
				flag = 1;
			}
		}
		return flag;
	}
	
	//�ӹ��ﳵ���Ƴ��鼮
	public void removeBook(Book book) throws NotFindException{
		int number = 0;
		if(searchBook(book) == 1) {
		    this.remove(book);
//		    for(Map.Entry<Book, Integer> map : this.entrySet()) {
//				if(book.getName().equals(map.getKey().getName()))  {
//					number = map.getValue();
//					break;
//				}
//		    }
	    } else {
	        throw new NotFindException(book.getName());	
	    }
		
	}
	
	public String toString() {
		String word = "";
	    for(Map.Entry<Book, Integer> map : this.entrySet()) {
			word += map.getKey().getBid()+""+map.getValue();
			
	    }
			return word;
	}
	

	//��������Ϣ�ӵ�orderCart
	public void addToOrderCart(Book book,int number) {
		this.put(book,number);
	}
//	
//	//�����±�ȥ�����鼮
//	public Book getBook(int bid) {
//		Object[] book = this.keySet().toArray();
//		return (Book)book[bid];
//	}
	
}
