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
	private ShoppingCart orderCart;//��¼����
	
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
		user = new UserDaoImpl();
		orderCart = new ShoppingCart();
	}
	
	//���е�¼,����˺��������Ƿ���ȷ
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
			if(type.equals("����Ա")) {
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
    		this.setTitle("��ʾ");
    		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		label = new JLabel("Ŀǰ���ݿ���û���û���Ϣ������ע��!");
    		this.add(label,BorderLayout.CENTER);
    		this.setLocation(150, 100);
    		this.setSize(250,100);
    		this.setVisible(true);
    	}
    }

	//���û��Ĺ��ﳵ��Ϣ�������ݿ�
	public void storeInfo(User user2) {
		user.coverFile(user2);
	}

	//�õ��û�
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
	
	//��������Ϣ���õ��ļ���
	public void setOrder(Book book,int number,User user1 ) {
		user.setOrder(book,number,user1);
//		System.out.println(book.getName()+number);
	}
	
	//���ļ��ж�ȡ������Ϣ
	public Set<User> getOrderInfo(){
		return user.getOrder();
	}
//	//ע���˺�
//	public void registerAcco() {
//		System.out.println("�����������ֻ���(�����ֻ����뽫Ϊ�����˺�)��");
//		Scanner sc = new Scanner(System.in);
//		String account = sc.next();
//		while(searchAcco(account) == 1) {
//			System.out.println("���˺��ѱ�ע�ᣬ���������룡");
//			account = sc.next();
//		}
//		
//		//��������һ��ȷ������ĸ�ȵ�Ҫ��
//		User user = null;
//		while(true) {
//		    System.out.println("����ע�����룺");
//		    String passwords1 = sc.next();
//		    System.out.println("�ٴ��������룺");
//		    String passwords2 = sc.next();
//		    if(passwords1.equals(passwords2)) {
//		        
//		        break;	
//		    } else {
//		    	System.out.println("�������벻һ�£�����������");
//		    }
//		}
//	}
//	public static void main(String[] args) {
//		UserServiceImpl.RemindRegister register = new UserServiceImpl().new RemindRegister();
//	}

}
