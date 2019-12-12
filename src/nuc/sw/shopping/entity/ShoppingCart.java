package nuc.sw.shopping.entity;

import java.util.*;
import nuc.sw.shopping.exception.*;

public class ShoppingCart extends HashMap<Book,Integer> {


	public ShoppingCart() {
		// TODO Auto-generated constructor stub
		super();

	}
	
	/**
	 * 将书籍添加进购物车,quantity为变化后的数量
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
	 * 减少购物车中的书籍，专给购物车的界面用
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
	 * 选择购物车里的书籍进行购买，这里还有问题，无法进行购买，需要修改代码
	 * 买书涉及到 1.用户文件更新(先把购物车给更新后直接进行coverFile(),在User操作中有) 2.用户购物车的更新 3.产生订单，另外需要一个文件去存放用户和购买的商品和数量
	 * 4.抛出异常时产生窗口
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

	//查看购物车里是否有这种书,没有返回0，有返回1
	public int searchBook(Book book) {
		int flag = 0;
		for(Book b :this.keySet()) {
			if(book.getName().equals(b.getName())) {
				flag = 1;
			}
		}
		return flag;
	}
	
	//从购物车中移除书籍
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
	

	//将订单信息加到orderCart
	public void addToOrderCart(Book book,int number) {
		this.put(book,number);
	}
//	
//	//根据下标去返回书籍
//	public Book getBook(int bid) {
//		Object[] book = this.keySet().toArray();
//		return (Book)book[bid];
//	}
	
}
