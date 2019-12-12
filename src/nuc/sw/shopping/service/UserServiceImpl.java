package nuc.sw.shopping.service;


import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import nuc.sw.shopping.dao.*;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.exception.BookException;

public class UserServiceImpl implements UserService {
	private UserDaoImpl user;
	private int flag ;
	private static User userNow;
	private ShoppingCart orderCart;//记录订单
	
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
		user = new UserDaoImpl();
		orderCart = new ShoppingCart();
	}
	
	//进行登录,检查账号与密码是否正确
	@Override
	public int log(String account,String type,String password) {
		// TODO Auto-generated method stub
		Set<User> users = user.getUser();
		if(users == null) {
			new RemindRegister();
			flag = 2;
		}
		Object[] userStr = users.toArray();
//		for(Object aUser : userStr) {
//			System.out.println(((User)aUser).getAccount() + ""+((User)aUser).getType()+ ((User)aUser).getPassword());
//		}
		User a = null;
		for(int i = 0;i < userStr.length;i ++) {
			a = (User)userStr[i];
			if(account.equals(a.getAccount())) {
				flag = 1;
				break;
			}
		}
		if(password.equals(a.getPassword()) && type.equals(a.getType())) {
			if(type.equals("管理员")) {
				return 2;
			} else {
				userNow = a;
				return 1;	
				
			}
		} else if(flag == 2){
		    return -1;
		} else {
			return 0;
		}


	
    }
    class RemindRegister extends JFrame{
    	
        private JLabel label;
    	public RemindRegister(){
    		this.setTitle("提示");
    		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		label = new JLabel("目前数据库里没有用户信息，请先注册!");
    		this.add(label,BorderLayout.CENTER);
    		this.setLocation(150, 100);
    		this.setSize(250,100);
    		this.setVisible(true);
    	}
    }

	//将用户的购物车信息存入数据库
	public void storeInfo(User user2) {
		user.coverFile(user2);
	}

	//得到用户
	public User getUser() {
		return userNow;
	}

	public void buyBook(Book book,int number) {
		try {
			userNow.getCart().buy(book, number);
		} catch (BookException e) {
			// TODO Auto-generated catch block
			e.warningFrame();
		}
		Set<Book> oldBooks = BookDaoImpl.getBooks();
		for(Book b: oldBooks) {
			if(book.getBid().equals(b.getBid())) {
				book.setNumber(b.getNumber() - number);
				oldBooks.remove(b);
				oldBooks.add(book);
				break;
			}		
		}
		BookDaoImpl.getNewBooks(oldBooks);
		BookDaoImpl.coverFile();
	}
	
	//将订单信息设置到文件中
	public void setOrder(Book book,int number,User user1 ) {
		user.setOrder(book,number,user1);
//		System.out.println(book.getName()+number);
	}
	
	//从文件中读取订单信息
	public Set<User> getOrderInfo(){
		return user.getOrder();
	}
//	//注册账号
//	public void registerAcco() {
//		System.out.println("请输入您的手机号(您的手机号码将为您的账号)：");
//		Scanner sc = new Scanner(System.in);
//		String account = sc.next();
//		while(searchAcco(account) == 1) {
//			System.out.println("此账号已被注册，请重新输入！");
//			account = sc.next();
//		}
//		
//		//可以增加一个确保有字母等的要求
//		User user = null;
//		while(true) {
//		    System.out.println("输入注册密码：");
//		    String passwords1 = sc.next();
//		    System.out.println("再次输入密码：");
//		    String passwords2 = sc.next();
//		    if(passwords1.equals(passwords2)) {
//		        
//		        break;	
//		    } else {
//		    	System.out.println("两次密码不一致，请重新输入");
//		    }
//		}
//	}
//	public static void main(String[] args) {
//		UserServiceImpl.RemindRegister register = new UserServiceImpl().new RemindRegister();
//	}

}
