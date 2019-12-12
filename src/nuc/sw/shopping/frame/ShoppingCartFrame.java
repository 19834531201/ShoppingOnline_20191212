package nuc.sw.shopping.frame;

import java.awt.*;
import java.awt.event.*;

import javax.management.loading.PrivateClassLoader;
import javax.swing.*;

import nuc.sw.shopping.dao.UserDaoImpl;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.exception.BookException;
import nuc.sw.shopping.exception.NotFindException;
import nuc.sw.shopping.service.UserServiceImpl;

/*
 * 这里选用了boxLayout的布局，
 * 为了删除购物车里的商品的时候方便删除，如果用表格布局的话不容易删除
 */
public class ShoppingCartFrame extends JFrame{

	private JPanel panel,bottomPanel;
	private JPanel[] panels;//多个面板用于放不同种类的书
	private JLabel[] labels;//多个标签用于放
	private JButton buttonAllSelect,buttumAllNumber,buttonDelete;
	private BoxLayout boxLayout;
	private JLabel label,labelAllMoney;
	private JRadioButton[] jrButtons;//单选按钮
	private MixButton[] mixButtons;
	private ShoppingCart myCart;
	private int size;
	private User user;
	private int numPrice;
	private int j;
	private Book[] books;
	private static int time = 0;//记录MixButton被创建对象的次数，方便获取下标，实现对价钱的更新
	private ImageIcon icon;
	
	public ShoppingCartFrame(User user) {
		// TODO Auto-generated constructor stub
	
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		this.myCart = user.getCart();
		size = myCart.size();
		this.user = user;
		this.setTitle("我的购物车");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		this.setSize(600,500);
		this.setVisible(true);
		
	}
	
	public void init() {
		panel = new JPanel();
		//到时候直接把购物车传来,根据集合的大小然后设置数组的大小
		/*
		 * //用循环连起来
		 * panels = new JPanel[length];
		 * buttons = new JButton[length * 2 + 1];
		 * int j = 0;
		 * for(int i = 0;i < length;i ++) {
		 *     panels[i] = new JPanel();
		 *     panels[i].setLayout(new GridLayout(1,1));
		 *     label[i] = new JLabel(book.getName());
		 *     buttons[++ j] = new JButton();//从1开始记录button的下标
		 *     Dimension preDimension = new Dimension(70,60);
		 *     button[j].setPreferredSize(preDimension);//设置按钮的大小
		 *     button[++ j] = new JButton();
		 *     panels[i].add(buttons[j - 1]);
		 *     panels[i].add(label[i]);
		 *     panels[i].add(buttons[j]);
		 *     panel.add(panels[i]);
		 * }
		 */
		 panels = new JPanel[size];
		 mixButtons = new MixButton[size];
		 labels = new JLabel[size * 2 + 1];
		 jrButtons = new JRadioButton[size];
		 int k = 0;
		 
		 Dimension preDimension;
		 Book book = null;
		 books = new Book[myCart.keySet().size()];
		 Object[] booksOb = myCart.keySet().toArray();
		 for(int i=0;i < myCart.keySet().size();i ++) {
			 books[i] = (Book)booksOb[i];
		 }
		 Object[] numbers = myCart.values().toArray();
			for(int i = 0;i < size;i ++) {
			     panels[i] = new JPanel();
			     panels[i].setLayout(new GridLayout(1,4));
			     labels[++ k] = new JLabel(books[i].getName(),JLabel.LEFT);
			     labels[k].setFont(new Font("宋体",Font.BOLD,20));
			     labels[++ k] = new JLabel("单价:￥" + books[i].getPrice(),JLabel.LEFT);
			     labels[k].setFont(new Font("宋体",Font.BOLD,20));
			     jrButtons[i] = new JRadioButton("",false);			     
			     mixButtons[i] = new MixButton((int)numbers[i],user);
			     
			     preDimension = new Dimension(70,60);
			     jrButtons[i].setPreferredSize(preDimension);//设置按钮的大小
			     panels[i].add(jrButtons[i]);
			     panels[i].add(labels[k - 1]);
			     panels[i].add(labels[k]);
			     panels[i].add(mixButtons[i]);
			     panel.add(panels[i]);
		     
			}
			
//		a = new JPanel();
//	     a.setLayout(new GridLayout(1,1));
//	     d = new JLabel("面向对象");
//	     b = new JButton("aa");//从1开始记录button的下标
//	     Dimension preDimension = new Dimension(70,60);
//	     b.setPreferredSize(preDimension);//设置按钮的大小
//	     c = new JButton("bb");
//	     a.add(b);
//	     a.add(d);
//	     a.add(c);
//		 panel.add(a);
//		 
//		 q = new JPanel();
//	     q.setLayout(new GridLayout(1,1));
//	     w = new JLabel("面向对象");
//	     e = new JButton("aa");//从1开始记录button的下标
//	     preDimension = new Dimension(70,60);
//	     e.setPreferredSize(preDimension);//设置按钮的大小
//	     r = new JButton("bb");
//	     q.add(e);
//	     q.add(w);
//	     q.add(r);
//		 panel.add(q);
		 
		buttonAllSelect = new JButton("全选");
		buttonAllSelect.setFont(new Font("宋体",Font.BOLD,20));
		buttumAllNumber = new JButton("结算");
		buttumAllNumber.setFont(new Font("宋体",Font.BOLD,20));
		labelAllMoney = new JLabel("总价为：￥0");
		labelAllMoney.setFont(new Font("宋体",Font.BOLD,20));
		buttonDelete = new JButton("移除");
		buttonDelete.setFont(new Font("宋体",Font.BOLD,20));
		bottomPanel = new JPanel();

//		Dimension preDimension = new Dimension(70,60);
//		a.setPreferredSize(preDimension);
		
		
		boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxLayout);
		
		
//
//		panel.add(panel1);
//		panel.add(panel2);
//		panel.add(b,BorderLayout.NORTH);
		
		
//		panel.remove(a);
		bottomPanel.setLayout(new GridLayout(1,4,5,5));
		bottomPanel.add(buttonAllSelect);
		bottomPanel.add(buttonDelete);
		bottomPanel.add(labelAllMoney);
		bottomPanel.add(buttumAllNumber);
		
		this.add(bottomPanel,BorderLayout.SOUTH);
		this.add(panel,BorderLayout.NORTH);

		buttonDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for(int i = 0; i < jrButtons.length; i ++) {
					if(jrButtons[i].isSelected()) {
						panel.remove(panels[i]);
						try {
							user.getCart().removeBook(books[i]);
						} catch (NotFindException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						UserDaoImpl.coverFile(user);
						renewFrame();
					}
				}
			}
		});
		
		buttonAllSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i = 0; i < jrButtons.length; i ++) {
					jrButtons[i].setSelected(true);
				}
			}
		});
		
		buttumAllNumber.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserServiceImpl userImpl = new UserServiceImpl();
				Book book =null;
				for(int i = 0; i < jrButtons.length; i ++) {
					book = books[i];
					if(jrButtons[i].isSelected()) {
						userImpl.buyBook(book, mixButtons[i].getNumber());
						panel.remove(panels[i]);
						renewFrame();
						userImpl.setOrder(book, mixButtons[i].getNumber(),user);
					}
				}
				UserDaoImpl.coverFile(user);
				new BuySuccessfully();
				labelAllMoney.setText("总价为:￥0");
			}
		});
		
		Object[] bookNumber = myCart.values().toArray();
		
		//不知道为什么当j到了循环的监听里时j就自动加了1,循环里的按钮会有问题，按钮的下标必须是常量
		for(j = 0;j < size; j ++) {
			final int i = j;
			jrButtons[j].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
					if(jrButtons[i].isSelected()) {
						numPrice += ((Book)books[i]).getPrice() * (int)bookNumber[i];
 					labelAllMoney.setText("总价为:￥"+numPrice);
					} else {
						numPrice -= ((Book)books[i]).getPrice() * (int)bookNumber[i];
						labelAllMoney.setText("总价为:￥"+numPrice);
					}
				}
			});
		}
		

	}
	
	class BuySuccessfully extends JFrame{
		private JLabel label;
		private JPanel panel;
		private JButton button;
		public BuySuccessfully() {
			// TODO Auto-generated constructor stub
			this.setTitle("购买成功");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(350,100);
			this.setVisible(true);
			this.setLocation(120, 180);
		}
		
		public void init() {
			label = new JLabel("购买成功，预计3~5天之内发货哦！",JLabel.CENTER);
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
	
	class MixButton extends Panel{

		
		private JButton addButton,cutButton;
		private JLabel label;
		private int number;
		private UserServiceImpl userImpl;
		private int seNumber;//为购物车创建的数组的下标
		
		public MixButton(int number,User user) {
			// TODO Auto-generated constructor stub
			seNumber = time ++;
			addButton = new JButton("+");
			 cutButton = new JButton("-");
			 this.number = number;
			 userImpl = new UserServiceImpl();
			 addButton.setFont(new Font("宋体",Font.BOLD,10));
			 cutButton.setFont(new Font("宋体",Font.BOLD,10));
			 Dimension preDimension = new Dimension(40,50);
		     addButton.setPreferredSize(preDimension);
		     Dimension pDimension = new Dimension(40,50);
		     cutButton.setPreferredSize(preDimension);
			 label = new JLabel(number + "");
			 
			 this.add(addButton);
			 this.add(label);
			 this.add(cutButton);
			 
			addButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
//					System.out.println("下标为"+seNumber);
					try {
						if(jrButtons[seNumber].isSelected()) {
							numPrice += ((Book)books[seNumber]).getPrice();
	 					labelAllMoney.setText("总价为:￥"+numPrice);
						}
						user.getCart().add(books[seNumber], (++ MixButton.this.number));
						label.setText(MixButton.this.number + "");
						userImpl.storeInfo(user);
						Object[] books = myCart.keySet().toArray();
					} catch (BookException e) {
						// TODO Auto-generated catch block
						e.warningFrame();;
					}

				}
			}); 
			
			cutButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(MixButton.this.number > 1) {
					try {
						if(jrButtons[seNumber].isSelected()) {
							numPrice -= ((Book)books[seNumber]).getPrice();
	 					labelAllMoney.setText("总价为:￥"+numPrice);
						}
						user.getCart().add(books[seNumber], (-- MixButton.this.number));
						label.setText(MixButton.this.number + "");
						userImpl.storeInfo(user);
						Object[] books = myCart.keySet().toArray();
					} catch (BookException e) {
						// TODO Auto-generated catch block
						e.warningFrame();;
					}

					}
				}
			});
		}

		public int getNumber() {
			return number;
		}
		

	}
	/**
	 * 刷新界面
	 */
	public void renewFrame() {
		this.invalidate();
		this.validate();
	}

}
