package nuc.sw.shopping.frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import nuc.sw.shopping.dao.BookDaoImpl;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.service.*;

public class UserMainFrame extends JFrame{

	private JMenuBar menuBar;
	private JMenu m_queryInfo,m_shoppingCart;
	private JMenuItem mi_queryAll,mi_queryById,mi_queryByName,mi_checkCart;
	private User user;
	private ImageIcon icon;
	
	public UserMainFrame(User user) {
		// TODO Auto-generated constructor stub	
		this.user = user;
		this.setTitle("电商购物平台系统--【欢迎用户" + user.getAccount() + "登录系统】");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		this.setSize(500,500);
		this.setVisible(true);
	}
	
	public void init() {
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		menuBar = new JMenuBar();
		m_queryInfo = new JMenu("查询书籍信息");
		mi_queryAll = new JMenuItem("查询所有书籍");
		mi_queryById = new JMenuItem("按书籍编号查询");
		mi_queryByName = new JMenuItem("按书籍名称查询");
		m_shoppingCart = new JMenu("我的购物车");
		mi_checkCart = new JMenuItem("查看购物车");
		
		m_shoppingCart.add(mi_checkCart);
		m_queryInfo.add(mi_queryAll);
		m_queryInfo.add(mi_queryByName);
		m_queryInfo.add(mi_queryById);
		menuBar.add(m_queryInfo);
		menuBar.add(m_shoppingCart);
		this.setJMenuBar(menuBar);
		this.getContentPane().setBackground(new Color(178,200,187));
		
		mi_queryAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SearchBook();
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
		
		mi_checkCart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
//				for(int i : user.getCart().values()) {
//					System.out.println(i);
//				}
				new ShoppingCartFrame(user);
			}
		});
		
	}
	
	class SearchBook extends JFrame{

		private JLabel l_book;
		private JTextField tf_book;
		private JButton button;
		private String[] book = {"编号","书名","作者","定价","库存"};
		private String[][] books;;
		private Book[] bookList;
		private BookDaoImpl bookImpl;
				
		
		public SearchBook() {
			// TODO Auto-generated constructor stub
			bookImpl = new BookDaoImpl();
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
			bookList = new Book[bookImpl.queryBooks().size()];
			Object[] ob = bookImpl.queryBooks().toArray();
			for(int i=0;i<bookImpl.queryBooks().size();i++) {
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

//			//实现表格的监听(响应有点慢)
//			table.getModel().addTableModelListener(new TableModelListener() {
//	            public void tableChanged(TableModelEvent e) {
//	                int row = e.getFirstRow(); 
//	                new AddBookToCartFrame(bookList[row], user);
//	            }
//	        });
			
			//用鼠标响应
			table.addMouseListener(new java.awt.event.MouseAdapter(){
	             public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
	                //得到选中的行列的索引值
	               int row= table.getSelectedRow();
//	               int col= table.getSelectedColumn();
	               //得到选中的单元格的值，表格中都是字符串
	                new AddBookToCartFrame(bookList[row], user);
	             }
	         });
			
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
		private JLabel label1,label2,label3,label4;
		private JTextField idField;
		private JButton searchButton,addButton;
		private BookDaoImpl bookDaoImpl;
		
		public QueryById(){
			this.setTitle("按编号查询");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(500, 125);
			this.setVisible(true);
		}
			
	    public void init() {
	    	bookDaoImpl = new BookDaoImpl();
			panel = new JPanel();
			label1 = new JLabel("请输入书籍编号:",JLabel.CENTER);
			label2 = new JLabel("",JLabel.CENTER);
			idField = new JTextField();
			label3 = new JLabel("");
			label4 = new JLabel("");
			addButton = new JButton("添加购物车");
			searchButton = new JButton("查询");
			panel.setLayout(new GridLayout(2,3,5,5));

			panel.add(label1);
			panel.add(idField);
			panel.add(searchButton);
			panel.add(label3);
			panel.add(label4);
			panel.add(addButton);
			
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
			
			addButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setVisi();
					String bid = idField.getText();
					Book book = bookDaoImpl.queryById(bid);
					new AddBookToCartFrame(book,user);
				}
			});
		}
	    
	    public void setVisi() {
	    	this.setVisible(false);
	    }
	}
    class QueryByName extends JFrame{
		
		private JPanel panel;
		private JLabel label1,label2,label3,label4;
		private JTextField nameField;
		private JButton searchButton,addButton;
		private BookServiceImpl bookServiceImpl;
		
		public QueryByName(){
			this.setTitle("按书名查询");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(500, 125);
			this.setVisible(true);
		}
			
	    public void init() {
	    	bookServiceImpl = new BookServiceImpl();
			panel = new JPanel();
			label1 = new JLabel("请输入书籍编号:",JLabel.CENTER);
			label2 = new JLabel("",JLabel.CENTER);
			nameField = new JTextField();
			label3 = new JLabel("");
			label4 = new JLabel("");
			addButton = new JButton("添加购物车");
			searchButton = new JButton("查询");
			panel.setLayout(new GridLayout(2,3,5,5));
			
			panel.add(label1);
			panel.add(nameField);
			panel.add(searchButton);
			panel.add(label3);
			panel.add(label4);
			panel.add(addButton);
			
			this.add(panel,BorderLayout.NORTH);
			this.add(label2,BorderLayout.SOUTH);
			this.setLocation(0, 150);
			
			searchButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String name = nameField.getText();
					Book book = bookServiceImpl.queryByName(name);
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
			
            addButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setVisi();
					String name = nameField.getText();
					Book book = bookServiceImpl.queryByName(name);
					new AddBookToCartFrame(book,user);
				}
			});
		}
	    
	    public void setVisi() {
	    	this.setVisible(false);
	    }
	}
	public static void main(String[] args) {
		new UserMainFrame(new User("1","1", "1"));
	}
	

}
