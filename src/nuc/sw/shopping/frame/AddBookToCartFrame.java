package nuc.sw.shopping.frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.exception.BookException;
import nuc.sw.shopping.service.UserServiceImpl;

public class AddBookToCartFrame extends JFrame{

	private Book book;
	private User user;
	private JLabel bookName1,number,bookName2,numberBuy;
	private JPanel bookNumber;
	private JButton addButton,cutButton,returnButton,addSuccess;
	private int i;//i为加入购物车的书的数量
	private ShoppingCart cart;
	private UserServiceImpl userImpl;
	
	public AddBookToCartFrame(Book book,User user) {
		// TODO Auto-generated constructor stub
	
		userImpl = new UserServiceImpl();
		this.book = book;
		this.user = user;
		this.cart = user.getCart();
		this.setTitle("加入购物车");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		this.setSize(270,150);
		this.setVisible(true);	
	}
	
	public void init() {
		this.setLayout(new GridLayout(3,2,8,8));
		bookName1 = new JLabel("书籍名称:",JLabel.CENTER);
		bookName2 = new JLabel(book.getName());
		number = new JLabel("购买数量:",JLabel.CENTER);
		bookNumber = new JPanel();
		addButton = new JButton("+");
		addButton.setFont(new Font("宋体",Font.BOLD,12));
		cutButton = new JButton("-");
		cutButton.setFont(new Font("宋体",Font.BOLD,12));
		i = 1;
		numberBuy = new JLabel("1",JLabel.CENTER);
		returnButton  = new JButton("返回");
		addSuccess = new JButton("确认");
		
		bookNumber.setLayout(new GridLayout(1,3,0,4));
		bookNumber.add(addButton);
		bookNumber.add(numberBuy);
		bookNumber.add(cutButton);
		
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				numberBuy.setText((++ i) + "");
			}
		});
		
		cutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(i > 1) {
					numberBuy.setText((-- i) + "");
				}
			}
		});
		
		addSuccess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisi();
				try {
					cart.add(book, i);
				} catch (BookException e) {
					// TODO Auto-generated catch block
					e.warningFrame();;
				}
//				System.out.println(book.getName());
//				for(Book b : cart.keySet()) {
//					System.out.println(b.getBid());
//				}
				userImpl.storeInfo(user);
				new AddSucessfully();
			}
		});
		
		returnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisi();
			}
		});
		
		this.add(bookName1);
		this.add(bookName2);
		this.add(number);
		this.add(bookNumber);
		this.add(returnButton);
		this.add(addSuccess);
		this.setLocation(70,150);
	}
	
	class AddSucessfully extends JFrame{
		
		private JLabel label;
		private JPanel panel;
		private JButton button;
		public AddSucessfully(){
			this.setTitle("添加成功");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(250,110);
			this.setVisible(true);
			this.setLocation(80, 180);
		}
		
		public void init() {
			label = new JLabel("添加成功，在购物车等亲~",JLabel.CENTER);
			panel = new JPanel();
			button = new JButton("确定");
			panel.add(button);
			this.add(label);
			this.add(panel,BorderLayout.SOUTH);
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setVisi();
				}
			});
		}
		
		public void setVisi() {
			this.setVisible(false);
		}
	}
	
	public void setVisi() {
		this.setVisible(false);
	}

}
