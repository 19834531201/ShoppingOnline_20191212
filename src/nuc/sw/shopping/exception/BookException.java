package nuc.sw.shopping.exception;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BookException extends Exception{

    private String bookName;
	private int bookNumber;
	public BookException(String bookName,int bookNumber) {
		// TODO Auto-generated constructor stub
		super();
	    this.bookNumber = bookNumber;
	    this.bookName = bookName;
	}
	
	public void warningFrame(){
		new WarningFrame();
	}
	
	class WarningFrame extends JFrame{
		private JPanel jp1;
		private JPanel jp2;
		private ImageIcon icon;
		private JLabel label1,label2;
		private JButton button;
		public WarningFrame() {
			// TODO Auto-generated constructor stub
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    this.setTitle("警告");
		    init();
		    this.setSize(400, 110);
		    this.setVisible(true);
		}
		
		public void init() {
			jp1 = new JPanel();
			jp2 = new JPanel();
			button = new JButton("确定");
			icon = new ImageIcon(".\\src\\nuc\\sw\\shopping\\images\\1.jpg");
		    label1 = new JLabel(icon);
		    label1.setSize(30, 30);
		    label2 = new JLabel("书籍：《" + bookName + "》仅剩" + bookNumber + "本，请重新选择！");
			
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
			this.setLocation(50, 70);
			
		}
				
		public void visi() {
			this.setVisible(false);
		}
	}

}
