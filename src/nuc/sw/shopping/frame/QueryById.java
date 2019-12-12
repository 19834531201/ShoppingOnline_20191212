package nuc.sw.shopping.frame;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class QueryById extends JFrame{

	private JLabel bidLa,nameLa,firstLevelLa,secondLevelLa,authorLa,priceLa,numberLa;
	private JTextField bidTF,nameTF,autherTF,priceTF,numberTF,firstLevelTF,secondLevelTF;
	private JButton addButton,resetButton;
	private ImageIcon icon;
	
    public QueryById() {		
		// TODO Auto-generated constructor stub
	
		this.setTitle("编号查找书籍");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    init();
		this.setSize(300,400);
		this.setVisible(true);
	}

	public void init() {
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		this.setLayout(new GridLayout(8,2,5,5));
		bidLa = new JLabel("编号",JLabel.CENTER);
		nameLa = new JLabel("书名",JLabel.CENTER);
		firstLevelLa = new JLabel("一级目录",JLabel.CENTER);
		secondLevelLa = new JLabel("二级目录",JLabel.CENTER);
		authorLa = new JLabel("作者",JLabel.CENTER);
		priceLa = new JLabel("单价",JLabel.CENTER);
		numberLa = new JLabel("库存",JLabel.CENTER);
		bidTF = new JTextField();
		nameTF = new JTextField();
		autherTF = new JTextField();
		priceTF = new JTextField();
		numberTF = new JTextField();
		addButton = new JButton("加入购物车");
		resetButton = new JButton("返回上一栏");
		firstLevelTF = new JTextField();
		secondLevelTF = new JTextField();
		
		
		this.add(bidLa);
		this.add(bidTF);
		this.add(nameLa);
		this.add(nameTF);
		this.add(firstLevelLa);
		this.add(firstLevelTF);
		this.add(secondLevelLa);
		this.add(secondLevelTF);
		this.add(authorLa);
		this.add(autherTF);
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
				autherTF.setText("");
				priceTF.setText("");
				numberTF.setText("");
				
			}
		});
		
		
	}
	

	
	public static void main(String[] args) {
		new QueryById();
	}
}

