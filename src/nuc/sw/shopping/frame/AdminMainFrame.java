package nuc.sw.shopping.frame;


import java.awt.*;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;
import nuc.sw.shopping.dao.BookDaoImpl;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.frame.AdminMainFrame.SearchBook.BookFrame;
import nuc.sw.shopping.service.UserServiceImpl;



public class AdminMainFrame extends JFrame{

	private JMenuBar menuBar;
	private JMenu m_book,m_order,m_user,m_queryInfo;
	private JMenuItem mi_queryAll,mi_queryById,mi_queryByName,mi_add,mi_delete,mi_orderInfo;
	private BookDaoImpl dao;
	private User user;
	private ImageIcon icon;
	
	public AdminMainFrame(User user) {
		// TODO Auto-generated constructor stub
		this.user = user;
	    this.setTitle("电商购物平台系统--" + "【欢迎管理员" + user.getAccount() + "登陆系统】");
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    dao = new BookDaoImpl();
	    unit();
		this.setSize(500, 500);
	    this.setVisible(true);
	}
	
	public void unit() {
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		menuBar = new JMenuBar();
		m_book = new JMenu("书籍管理");
		m_order = new JMenu("订单管理");
		m_user = new JMenu("用户管理");
		m_queryInfo = new JMenu("查询商品信息");
		
		mi_queryAll = new JMenuItem("查询所有书籍");
		mi_queryById = new JMenuItem("按书籍编号查询");
		mi_queryByName = new JMenuItem("按书籍名称查询");
		mi_add = new JMenuItem("添加书籍");
		mi_delete = new JMenuItem("删除书籍");
		mi_orderInfo = new JMenuItem("查看用户订单");
		
		
		m_queryInfo.add(mi_queryAll);
		m_queryInfo.add(mi_queryById);
		m_queryInfo.add(mi_queryByName);
		m_order.add(mi_orderInfo);
		m_book.add(m_queryInfo);
		m_book.add(mi_add);
		m_book.add(mi_delete);
		menuBar.add(m_book);
		menuBar.add(m_order);
		menuBar.add(m_user);
		this.add(menuBar);
		this.setJMenuBar(menuBar);
		this.getContentPane().setBackground(new Color(178, 200, 187));
		
		mi_queryAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    new SearchBook();
			}
		});
		
		mi_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new AddBook();
			}
		});
		
		mi_queryById.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new QueryById();
			}
		});
		
		mi_queryByName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new QueryByName();
			}
		});
		
		mi_delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new DeleteBook();
			}
		});
		
		mi_orderInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				UserServiceImpl userImpl = new UserServiceImpl();
				Set<User> userOrder = userImpl.getOrderInfo();
				Object[] ob = userOrder.toArray();
				User[] users = new User[userOrder.size()];
				for(int i = 0; i < userOrder.size(); i ++) {
					users[i] = (User)ob[i];
				}
				new OrderFrame(users);
			}
		});
		
	}
	public void setVisible() {
		this.setVisible(false);
	}

	class SearchBook extends JFrame{

		private JLabel l_book;
		private JTextField tf_book;
		private JButton button;
		private String[] book = {"编号","书名","作者","定价","库存"};
		private String[][] books;
		private Book[] bookList;
				
		
		public SearchBook() {
			// TODO Auto-generated constructor stub
	        this.setTitle("电商购物平台—商品查询界面");	
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        init();
	        this.setSize(500,400);
	        this.setVisible(true);
		}
		
		public void init() {
			l_book = new JLabel("书籍名",JLabel.CENTER);
			tf_book = new JTextField();
			button = new JButton("查询");
			Object[] ob= dao.queryBooks().toArray();
			bookList = new Book[ob.length];
			for(int i = 0;i < ob.length;i ++) {
				bookList[i] = (Book)ob[i];
			}
			books = new String[bookList.length][book.length];
			for(int i = 0; i < bookList.length; i ++) {
				for(int j = 0; j < book.length; j ++) {
					switch (j) {
					case 0: books[i][j] = bookList[i].getBid();
		            break;
					case 1: books[i][j] = bookList[i].getName();
	                break;
					case 2: books[i][j] = bookList[i].getAuthor();
	                break;
					case 3: books[i][j] = String.valueOf(bookList[i].getPrice());
	                break;
					case 4: books[i][j] = String.valueOf(bookList[i].getNumber());
	                break;
					default:
						break;
					}
				}
			}
			//自定义布局
			this.setLayout(null);
			JTable table = new JTable(books,book);
			l_book.setBounds(10, 10, 100, 30);
			tf_book.setBounds(130, 10, 200, 30);
			button.setBounds(350, 10, 100, 30);
//			table.setBounds(10, 90, 400, 100);
			
			this.add(l_book);
			this.add(tf_book);
			this.add(button);
			//将表格加到JScrollPane中
			JScrollPane jScrollPane = new JScrollPane(table);
			jScrollPane.setBounds(10, 50, 450, 100);
			//取得表头再进行添加
//			JTableHeader header = table.getTableHeader();
//			header.setBounds(10,210,400,10);
//			this.add(table);
//			this.add(header);
			this.add(jScrollPane);
			//只有直接在窗体上显示的才可以setBounds(),上面先把table给setBounds(),再加到JScrollPane()中发现表格不显示,因为无法再JScroollPane中设置位置
            button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					BookDaoImpl bookImpl = new BookDaoImpl();
					Book book = bookImpl.queryByName(String.valueOf(tf_book.getText()));
					new BookFrame(book);
				}
			});
		}
        class BookFrame extends JFrame {
			
			private Book book;
			private JLabel label;
			private JButton button;

			public BookFrame(Book book) {
				this.setTitle("书籍信息");
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.book = book;
				if(book != null) {
				label = new JLabel(book.toString(),JLabel.CENTER);
				} else {
					label = new JLabel("很抱歉，没有找到这本书！",JLabel.CENTER);
				}
				button = new JButton("确定");
				this.setLayout(new GridLayout(2,1,5,5));
				this.add(label);
				this.add(button);
				this.setSize(500,100);
				this.setVisible(true);
				this.setLocation(0,100);
				
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
		

	}
	class QueryById extends JFrame{
		
		private JPanel panel;
		private JLabel label1,label2;
		private JTextField idField;
		private JButton searchButton;
		private BookDaoImpl bookDaoImpl;
		
		public QueryById(){
			this.setTitle("按编号查询");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(500, 90);
			this.setVisible(true);
		}
			
	    public void init() {
	    	bookDaoImpl = new BookDaoImpl();
			panel = new JPanel();
			label1 = new JLabel("请输入书籍编号:",JLabel.CENTER);
			label2 = new JLabel("",JLabel.CENTER);
			idField = new JTextField();
			searchButton = new JButton("查询");
			panel.setLayout(new GridLayout(1,3,5,5));

			panel.add(label1);
			panel.add(idField);
			panel.add(searchButton);
			
			this.add(panel,BorderLayout.NORTH);
			this.add(label2,BorderLayout.SOUTH);
			this.setLocation(0, 150);
			
			searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String bid = idField.getText();
					Book book = bookDaoImpl.queryById(bid);
					if(bid.equals("") != true) {
				    	if(book != null) {
				    		label2.setText(book.toString());
				    	} else {
				    		label2.setText("抱歉，目前没有所查书籍！");
				    	}
					} else {
						label2.setText("请输入书籍后再进行查询！");
					}

				}
			});
		}
	}
    class QueryByName extends JFrame{
		
		private JPanel panel;
		private JLabel label1,label2;
		private JTextField nameField;
		private JButton searchButton;
		private BookDaoImpl bookDaoImpl;
		
		public QueryByName(){
			this.setTitle("按书名查询");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(500, 90);
			this.setVisible(true);
		}
			
	    public void init() {
	    	bookDaoImpl = new BookDaoImpl();
			panel = new JPanel();
			label1 = new JLabel("请输入书籍名:",JLabel.CENTER);
			label2 = new JLabel("",JLabel.CENTER);
			nameField = new JTextField();
			searchButton = new JButton("查询");
			panel.setLayout(new GridLayout(1,3,5,5));
			
			panel.add(label1);
			panel.add(nameField);
			panel.add(searchButton);
			
			this.add(panel,BorderLayout.NORTH);
			this.add(label2,BorderLayout.SOUTH);
			this.setLocation(0, 150);
			
			searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String name = nameField.getText();
					Book book = bookDaoImpl.queryByName(name);
					if(name.equals("") != true) {
						if(book != null) {
						    label2.setText(book.toString());
						} else {
							label2.setText("抱歉，目前没有所查书籍！");
						}
					} else {
						label2.setText("请输入书籍后再进行查询！");
					}

				}
			});
		}
	}
    
    class DeleteBook extends JFrame{
    	
    	private JLabel label;
    	private JPanel bidPanel;
    	private JTextField bidField;
    	private JButton returnButton,verifyButton;
    	
    	public DeleteBook() {
    		this.setTitle("删除书籍");
    		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		
    		label = new JLabel("请输入书籍编号：",JLabel.CENTER);
    		bidField = new JTextField();
    		bidPanel = new JPanel();
    		returnButton = new JButton("返回");
    		verifyButton = new JButton("删除");
    		bidPanel.setLayout(new GridLayout(2,2,5,5));
    		bidPanel.add(label);
    		bidPanel.add(bidField);
    		bidPanel.add(returnButton);
    		bidPanel.add(verifyButton);
    		this.add(bidPanel);
    		this.setSize(300,100);
    		this.setVisible(true);
    		this.setLocation(20, 100);
    		
    		returnButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setVisi();
				}
			});
    		
    		verifyButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
					new verifyDelete(bidField.getText());
					setVisi();
				}
			});
    		
    	}
    	
    	class verifyDelete extends JFrame{
    		
    		private JPanel panel;
    		private JLabel infoJLabel,label;
    		private JButton returnButton,deleteButton;
    		private Book book;
    		
    		public verifyDelete(String info) {
    			this.setTitle("提示");
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			panel = new JPanel(); 		
    			book = dao.queryById(info);
    			if(book != null) {
    			    infoJLabel = new JLabel(book.getName(),JLabel.CENTER);
    			    label = new JLabel("确认删除吗？",JLabel.CENTER);
        			returnButton = new JButton("还是算了");
        			deleteButton = new JButton("确认删除");
    			}    else {
    				infoJLabel = new JLabel("没有找到您的所选书籍",JLabel.CENTER);
    				label = new JLabel("请返回重新输入!",JLabel.CENTER);
        			returnButton = new JButton("回到首页");
        			deleteButton = new JButton("点我返回");
    			}
    			panel.setLayout(new GridLayout(2,2,5,5));
    			panel.add(infoJLabel);
    			panel.add(label);
    			panel.add(returnButton);
    			panel.add(deleteButton);
    			this.add(panel);
    			this.setSize(300,100);
    			this.setVisible(true);
    			this.setLocation(20, 100);
    			
    			returnButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						setVisible();
					}
				});
    			
    			deleteButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(book != null) {
						    dao.deleteBook(book);
						    infoJLabel.setText("恭喜您删除成功！");
						    label.setText("点右上角退出");
						    label.setForeground(Color.RED);
						} else {
							setVisible();
							new DeleteBook();
						}
					}
				});
    			
    		}
    		
    		public void setVisible() {
    			this.setVisible(false);
    		}
    	}
    	
    	public void setVisi() {
    		this.setVisible(false);
    	}
    	  	
    }
    
    class OrderFrame extends JFrame{
    	
    	private JPanel panel;
    	private JButton[] buttons;
    	private JLabel[] labels1;
    	private JLabel[] labels2;
    	private int size;
    	private User[] users;
    	private JLabel nameJLabel,typeJLabel,infoJLabel;
    	
    	public OrderFrame(User[] users) {
    		this.users = users;
    		this.setTitle("订单管理");
    		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		init();
    		this.setVisible(true);
    		this.setSize(400,300);
    	}
    	
    	public void init() {
    		panel = new JPanel();
    		size = users.length;
    		buttons = new JButton[size];
    		labels1 = new JLabel[size];
    		labels2 = new JLabel[size];
    		nameJLabel = new JLabel("用户",JLabel.CENTER);
    		typeJLabel = new JLabel("类型",JLabel.CENTER);
    		infoJLabel = new JLabel("信息",JLabel.CENTER);
    		panel.setLayout(new GridLayout(size+1,3,5,5));
    		panel.add(nameJLabel);
    		panel.add(typeJLabel);
    		panel.add(infoJLabel);
    		
    		for(int i = 0; i < size;i ++) {
    			labels1[i] = new JLabel(users[i].getAccount(),JLabel.CENTER);
    			labels2[i] = new JLabel(users[i].getType(),JLabel.CENTER);
    			buttons[i] = new JButton("点我查看详情");
    			panel.add(labels1[i]);
    			panel.add(labels2[i]);
    			panel.add(buttons[i]);  			
    		}
    		
    		this.add(panel,BorderLayout.NORTH);
    		
    		for(int i = 0; i < size;i ++) {
    			final int j = i;
    			buttons[j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						new ShowOrder(users[j]);
					}
				});
    		}
    	}
    	
    	class ShowOrder extends JFrame{
    		
 
    		private JPanel pane;
    		private ShoppingCart cart;
    		private int cartSize;
    		private JLabel[] nameLabels;
    		private JLabel[] numberLabels;
    		private JLabel[] numPriceLabels;
    		private Book[] books;
    		private int[] bookNumber;
    		private JLabel bookJLabel,numberJLabel,priceJLabel;
    		
    		public ShowOrder(User user) {
    			this.setTitle("订单详情");
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			init(user);
    			this.setSize(400,300);
    			this.setVisible(true);
    		}
    		
    		public void init(User user) {
    			pane = new JPanel();
    			cart = user.getCart();
    			cartSize = cart.size();
    			bookJLabel = new JLabel("书籍名称",JLabel.CENTER);
    			numberJLabel = new JLabel("书籍数量",JLabel.CENTER);
    			priceJLabel = new JLabel("总价",JLabel.CENTER);
    			nameLabels = new JLabel[cartSize];
    			numberLabels = new JLabel[cartSize];
    			numPriceLabels = new JLabel[cartSize];
    			books = new Book[cartSize];
    			bookNumber = new int[cartSize];
    			Object[] ObBook = cart.keySet().toArray();
    			Object[] number = cart.values().toArray();
    			for(int i = 0; i < cartSize;i++) {
    				books[i] = (Book)ObBook[i];
    				bookNumber[i] = (int)number[i];
    			}
    			pane.setLayout(new GridLayout(cartSize+1,3,5,5));
   			    pane.add(bookJLabel);
    			pane.add(numberJLabel);
   			    pane.add(priceJLabel);
    		    for(int i = 0; i < cartSize; i++) {
    				nameLabels[i] = new JLabel(books[i].getName(),JLabel.CENTER);
    				numberLabels[i] = new JLabel(bookNumber[i]+"",JLabel.CENTER);
    				numPriceLabels[i] = new JLabel((bookNumber[i] * books[i].getPrice()) +"",JLabel.CENTER);
    				pane.add(nameLabels[i]);
    				pane.add(numberLabels[i]);
    				pane.add(numPriceLabels[i]);
    			
    			}
    			this.add(pane,BorderLayout.NORTH);

    		}
    	}
    }
	


	

}
