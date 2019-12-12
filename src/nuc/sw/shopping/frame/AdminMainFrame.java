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
	    this.setTitle("���̹���ƽ̨ϵͳ--" + "����ӭ����Ա" + user.getAccount() + "��½ϵͳ��");
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
		m_book = new JMenu("�鼮����");
		m_order = new JMenu("��������");
		m_user = new JMenu("�û�����");
		m_queryInfo = new JMenu("��ѯ��Ʒ��Ϣ");
		
		mi_queryAll = new JMenuItem("��ѯ�����鼮");
		mi_queryById = new JMenuItem("���鼮��Ų�ѯ");
		mi_queryByName = new JMenuItem("���鼮���Ʋ�ѯ");
		mi_add = new JMenuItem("����鼮");
		mi_delete = new JMenuItem("ɾ���鼮");
		mi_orderInfo = new JMenuItem("�鿴�û�����");
		
		
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
		private String[] book = {"���","����","����","����","���"};
		private String[][] books;
		private Book[] bookList;
				
		
		public SearchBook() {
			// TODO Auto-generated constructor stub
	        this.setTitle("���̹���ƽ̨����Ʒ��ѯ����");	
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        init();
	        this.setSize(500,400);
	        this.setVisible(true);
		}
		
		public void init() {
			l_book = new JLabel("�鼮��",JLabel.CENTER);
			tf_book = new JTextField();
			button = new JButton("��ѯ");
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
			//�Զ��岼��
			this.setLayout(null);
			JTable table = new JTable(books,book);
			l_book.setBounds(10, 10, 100, 30);
			tf_book.setBounds(130, 10, 200, 30);
			button.setBounds(350, 10, 100, 30);
//			table.setBounds(10, 90, 400, 100);
			
			this.add(l_book);
			this.add(tf_book);
			this.add(button);
			//�����ӵ�JScrollPane��
			JScrollPane jScrollPane = new JScrollPane(table);
			jScrollPane.setBounds(10, 50, 450, 100);
			//ȡ�ñ�ͷ�ٽ������
//			JTableHeader header = table.getTableHeader();
//			header.setBounds(10,210,400,10);
//			this.add(table);
//			this.add(header);
			this.add(jScrollPane);
			//ֻ��ֱ���ڴ�������ʾ�Ĳſ���setBounds(),�����Ȱ�table��setBounds(),�ټӵ�JScrollPane()�з��ֱ����ʾ,��Ϊ�޷���JScroollPane������λ��
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
				this.setTitle("�鼮��Ϣ");
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.book = book;
				if(book != null) {
				label = new JLabel(book.toString(),JLabel.CENTER);
				} else {
					label = new JLabel("�ܱ�Ǹ��û���ҵ��Ȿ�飡",JLabel.CENTER);
				}
				button = new JButton("ȷ��");
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
			this.setTitle("����Ų�ѯ");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(500, 90);
			this.setVisible(true);
		}
			
	    public void init() {
	    	bookDaoImpl = new BookDaoImpl();
			panel = new JPanel();
			label1 = new JLabel("�������鼮���:",JLabel.CENTER);
			label2 = new JLabel("",JLabel.CENTER);
			idField = new JTextField();
			searchButton = new JButton("��ѯ");
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
				    		label2.setText("��Ǹ��Ŀǰû�������鼮��");
				    	}
					} else {
						label2.setText("�������鼮���ٽ��в�ѯ��");
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
			this.setTitle("��������ѯ");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(500, 90);
			this.setVisible(true);
		}
			
	    public void init() {
	    	bookDaoImpl = new BookDaoImpl();
			panel = new JPanel();
			label1 = new JLabel("�������鼮��:",JLabel.CENTER);
			label2 = new JLabel("",JLabel.CENTER);
			nameField = new JTextField();
			searchButton = new JButton("��ѯ");
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
							label2.setText("��Ǹ��Ŀǰû�������鼮��");
						}
					} else {
						label2.setText("�������鼮���ٽ��в�ѯ��");
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
    		this.setTitle("ɾ���鼮");
    		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    		
    		label = new JLabel("�������鼮��ţ�",JLabel.CENTER);
    		bidField = new JTextField();
    		bidPanel = new JPanel();
    		returnButton = new JButton("����");
    		verifyButton = new JButton("ɾ��");
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
    			this.setTitle("��ʾ");
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			panel = new JPanel(); 		
    			book = dao.queryById(info);
    			if(book != null) {
    			    infoJLabel = new JLabel(book.getName(),JLabel.CENTER);
    			    label = new JLabel("ȷ��ɾ����",JLabel.CENTER);
        			returnButton = new JButton("��������");
        			deleteButton = new JButton("ȷ��ɾ��");
    			}    else {
    				infoJLabel = new JLabel("û���ҵ�������ѡ�鼮",JLabel.CENTER);
    				label = new JLabel("�뷵����������!",JLabel.CENTER);
        			returnButton = new JButton("�ص���ҳ");
        			deleteButton = new JButton("���ҷ���");
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
						    infoJLabel.setText("��ϲ��ɾ���ɹ���");
						    label.setText("�����Ͻ��˳�");
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
    		this.setTitle("��������");
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
    		nameJLabel = new JLabel("�û�",JLabel.CENTER);
    		typeJLabel = new JLabel("����",JLabel.CENTER);
    		infoJLabel = new JLabel("��Ϣ",JLabel.CENTER);
    		panel.setLayout(new GridLayout(size+1,3,5,5));
    		panel.add(nameJLabel);
    		panel.add(typeJLabel);
    		panel.add(infoJLabel);
    		
    		for(int i = 0; i < size;i ++) {
    			labels1[i] = new JLabel(users[i].getAccount(),JLabel.CENTER);
    			labels2[i] = new JLabel(users[i].getType(),JLabel.CENTER);
    			buttons[i] = new JButton("���Ҳ鿴����");
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
    			this.setTitle("��������");
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			init(user);
    			this.setSize(400,300);
    			this.setVisible(true);
    		}
    		
    		public void init(User user) {
    			pane = new JPanel();
    			cart = user.getCart();
    			cartSize = cart.size();
    			bookJLabel = new JLabel("�鼮����",JLabel.CENTER);
    			numberJLabel = new JLabel("�鼮����",JLabel.CENTER);
    			priceJLabel = new JLabel("�ܼ�",JLabel.CENTER);
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
