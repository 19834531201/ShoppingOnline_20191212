package nuc.sw.shopping.frame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import nuc.sw.shopping.entity.Book;
import nuc.sw.shopping.entity.Category;
import nuc.sw.shopping.service.BookServiceImpl;

public class AddBook extends JFrame{

	private JLabel bidLa,nameLa,firstLevelLa,secondLevelLa,authorLa,priceLa,numberLa,cateBidLa;
	private JTextField bidTF,nameTF,authorTF,priceTF,numberTF,cateBidTF;
	private JComboBox firstLevel,secondLevel;
	private JButton addButton,resetButton;
	private ImageIcon icon;
	
	public AddBook() {
		// TODO Auto-generated constructor stub
	
		this.setTitle("����鼮");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    init();
		this.setSize(300,450);
		this.setVisible(true);
	}

	public void init() {
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		this.setLayout(new GridLayout(9,2,5,5));
		bidLa = new JLabel("���",JLabel.CENTER);
		nameLa = new JLabel("����",JLabel.CENTER);
		firstLevelLa = new JLabel("һ��Ŀ¼",JLabel.CENTER);
		secondLevelLa = new JLabel("����Ŀ¼",JLabel.CENTER);
		authorLa = new JLabel("����",JLabel.CENTER);
		priceLa = new JLabel("����",JLabel.CENTER);
		numberLa = new JLabel("���",JLabel.CENTER);
		cateBidLa = new JLabel("Ŀ¼���",JLabel.CENTER);
		cateBidTF = new JTextField();
		bidTF = new JTextField();
		nameTF = new JTextField();
		authorTF = new JTextField();
		priceTF = new JTextField();
		numberTF = new JTextField();
		firstLevel = new JComboBox();
		secondLevel = new JComboBox();
		addButton = new JButton("���");
		resetButton = new JButton("����");
		firstLevel.addItem("������");
		firstLevel.addItem("С˵��");
		secondLevel.addItem("��ʷ");
		secondLevel.addItem("������");
		
		this.add(bidLa);
		this.add(bidTF);
		this.add(nameLa);
		this.add(nameTF);
		this.add(cateBidLa);
		this.add(cateBidTF);
		this.add(firstLevelLa);
		this.add(firstLevel);
		this.add(secondLevelLa);
		this.add(secondLevel);
		this.add(authorLa);
		this.add(authorTF);
		this.add(priceLa);
		this.add(priceTF);
		this.add(numberLa);
		this.add(numberTF);
		this.add(resetButton);
		this.add(addButton);
		
		
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bidTF.setText("");
				nameTF.setText("");
				authorTF.setText("");
				priceTF.setText("");
				numberTF.setText("");
				cateBidTF.setText(""); 
				
			}
		});
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Book book = new Book(bidTF.getText(),nameTF.getText(),authorTF.getText(),Integer.parseInt(numberTF.getText()),new Category(Integer.parseInt(cateBidTF.getText()),String.valueOf(firstLevel.getSelectedItem()),String.valueOf(secondLevel.getSelectedItem())),Double.parseDouble(priceTF.getText()));
				BookServiceImpl.addBook(book);
				new InfoFrame();
				
			}
		});
	}
	

	class InfoFrame extends JFrame{
		private JPanel jp1;
		private JPanel jp2;
		private ImageIcon icon;
		private JLabel label1,label2;
		private JButton button;
		public InfoFrame() {
			// TODO Auto-generated constructor stub
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    this.setTitle("��Ϣ");
		    init();
		    this.setSize(250, 110);
		    this.setVisible(true);
		}
		
		public void init() {
			jp1 = new JPanel();
			jp2 = new JPanel();
			button = new JButton("ȷ��");
		    label1 = new JLabel(icon);
		    label1.setSize(30, 30);
		    label2 = new JLabel("����鼮�ɹ���");
			
			jp1.add(label1);
			jp1.add(label2);
			jp2.add(button);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					visi();
				}
			});
			
			this.add(jp1,BorderLayout.NORTH);
			this.add(jp2,BorderLayout.SOUTH);
			this.setLocation(50, 110);
			
		}
		
		public void visi() {
			this.setVisible(false);
		}
	}
	public static void main(String[] args) {
		new AddBook().new InfoFrame();
	}
	
	
}
