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
 * ����ѡ����boxLayout�Ĳ��֣�
 * Ϊ��ɾ�����ﳵ�����Ʒ��ʱ�򷽱�ɾ��������ñ�񲼾ֵĻ�������ɾ��
 */
public class ShoppingCartFrame extends JFrame{

	private JPanel panel,bottomPanel;
	private JPanel[] panels;//���������ڷŲ�ͬ�������
	private JLabel[] labels;//�����ǩ���ڷ�
	private JButton buttonAllSelect,buttumAllNumber,buttonDelete;
	private BoxLayout boxLayout;
	private JLabel label,labelAllMoney;
	private JRadioButton[] jrButtons;//��ѡ��ť
	private MixButton[] mixButtons;
	private ShoppingCart myCart;
	private int size;
	private User user;
	private int numPrice;
	private int j;
	private Book[] books;
	private static int time = 0;//��¼MixButton����������Ĵ����������ȡ�±꣬ʵ�ֶԼ�Ǯ�ĸ���
	private ImageIcon icon;
	
	public ShoppingCartFrame(User user) {
		// TODO Auto-generated constructor stub
	
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		this.myCart = user.getCart();
		size = myCart.size();
		this.user = user;
		this.setTitle("�ҵĹ��ﳵ");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		init();
		this.setSize(600,500);
		this.setVisible(true);
		
	}
	
	public void init() {
		panel = new JPanel();
		//��ʱ��ֱ�Ӱѹ��ﳵ����,���ݼ��ϵĴ�СȻ����������Ĵ�С
		/*
		 * //��ѭ��������
		 * panels = new JPanel[length];
		 * buttons = new JButton[length * 2 + 1];
		 * int j = 0;
		 * for(int i = 0;i < length;i ++) {
		 *     panels[i] = new JPanel();
		 *     panels[i].setLayout(new GridLayout(1,1));
		 *     label[i] = new JLabel(book.getName());
		 *     buttons[++ j] = new JButton();//��1��ʼ��¼button���±�
		 *     Dimension preDimension = new Dimension(70,60);
		 *     button[j].setPreferredSize(preDimension);//���ð�ť�Ĵ�С
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
			     labels[k].setFont(new Font("����",Font.BOLD,20));
			     labels[++ k] = new JLabel("����:��" + books[i].getPrice(),JLabel.LEFT);
			     labels[k].setFont(new Font("����",Font.BOLD,20));
			     jrButtons[i] = new JRadioButton("",false);			     
			     mixButtons[i] = new MixButton((int)numbers[i],user);
			     
			     preDimension = new Dimension(70,60);
			     jrButtons[i].setPreferredSize(preDimension);//���ð�ť�Ĵ�С
			     panels[i].add(jrButtons[i]);
			     panels[i].add(labels[k - 1]);
			     panels[i].add(labels[k]);
			     panels[i].add(mixButtons[i]);
			     panel.add(panels[i]);
		     
			}
			
//		a = new JPanel();
//	     a.setLayout(new GridLayout(1,1));
//	     d = new JLabel("�������");
//	     b = new JButton("aa");//��1��ʼ��¼button���±�
//	     Dimension preDimension = new Dimension(70,60);
//	     b.setPreferredSize(preDimension);//���ð�ť�Ĵ�С
//	     c = new JButton("bb");
//	     a.add(b);
//	     a.add(d);
//	     a.add(c);
//		 panel.add(a);
//		 
//		 q = new JPanel();
//	     q.setLayout(new GridLayout(1,1));
//	     w = new JLabel("�������");
//	     e = new JButton("aa");//��1��ʼ��¼button���±�
//	     preDimension = new Dimension(70,60);
//	     e.setPreferredSize(preDimension);//���ð�ť�Ĵ�С
//	     r = new JButton("bb");
//	     q.add(e);
//	     q.add(w);
//	     q.add(r);
//		 panel.add(q);
		 
		buttonAllSelect = new JButton("ȫѡ");
		buttonAllSelect.setFont(new Font("����",Font.BOLD,20));
		buttumAllNumber = new JButton("����");
		buttumAllNumber.setFont(new Font("����",Font.BOLD,20));
		labelAllMoney = new JLabel("�ܼ�Ϊ����0");
		labelAllMoney.setFont(new Font("����",Font.BOLD,20));
		buttonDelete = new JButton("�Ƴ�");
		buttonDelete.setFont(new Font("����",Font.BOLD,20));
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
				labelAllMoney.setText("�ܼ�Ϊ:��0");
			}
		});
		
		Object[] bookNumber = myCart.values().toArray();
		
		//��֪��Ϊʲô��j����ѭ���ļ�����ʱj���Զ�����1,ѭ����İ�ť�������⣬��ť���±�����ǳ���
		for(j = 0;j < size; j ++) {
			final int i = j;
			jrButtons[j].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
					if(jrButtons[i].isSelected()) {
						numPrice += ((Book)books[i]).getPrice() * (int)bookNumber[i];
 					labelAllMoney.setText("�ܼ�Ϊ:��"+numPrice);
					} else {
						numPrice -= ((Book)books[i]).getPrice() * (int)bookNumber[i];
						labelAllMoney.setText("�ܼ�Ϊ:��"+numPrice);
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
			this.setTitle("����ɹ�");
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			init();
			this.setSize(350,100);
			this.setVisible(true);
			this.setLocation(120, 180);
		}
		
		public void init() {
			label = new JLabel("����ɹ���Ԥ��3~5��֮�ڷ���Ŷ��",JLabel.CENTER);
			panel = new JPanel();
			button = new JButton("ȷ��");
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
		private int seNumber;//Ϊ���ﳵ������������±�
		
		public MixButton(int number,User user) {
			// TODO Auto-generated constructor stub
			seNumber = time ++;
			addButton = new JButton("+");
			 cutButton = new JButton("-");
			 this.number = number;
			 userImpl = new UserServiceImpl();
			 addButton.setFont(new Font("����",Font.BOLD,10));
			 cutButton.setFont(new Font("����",Font.BOLD,10));
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
					
//					System.out.println("�±�Ϊ"+seNumber);
					try {
						if(jrButtons[seNumber].isSelected()) {
							numPrice += ((Book)books[seNumber]).getPrice();
	 					labelAllMoney.setText("�ܼ�Ϊ:��"+numPrice);
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
	 					labelAllMoney.setText("�ܼ�Ϊ:��"+numPrice);
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
	 * ˢ�½���
	 */
	public void renewFrame() {
		this.invalidate();
		this.validate();
	}

}
