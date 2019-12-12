package nuc.sw.shopping.frame;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.*;
import nuc.sw.shopping.dao.UserDaoImpl;
import nuc.sw.shopping.entity.User;
import nuc.sw.shopping.service.UserServiceImpl;

public class LogFrame extends JFrame{

	private JLabel account,type,password;
	private JTextField accoField;
	private JPasswordField passField;
	private JButton logButton , reSetButton,registerButton;
	private JPanel jp1,jp2,jp3;
	private JComboBox jcb;
	private BoxLayout bl;
	private UserServiceImpl userService;
	private User user;
	private ImageIcon icon;
	
	public LogFrame(UserServiceImpl userService) {
		// TODO Auto-generated constructor stub
	    this.userService = userService;
	    this.setTitle("登录注册窗体");
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    init();
	    this.setSize(300,300);
	    this.setVisible(true);
	}
	
	public void init() {
		icon = new ImageIcon("src/nuc/sw/shopping/images/LOGO.jpg");
		this.setIconImage(icon.getImage());
		account = new JLabel("用户名",JLabel.CENTER);
		type = new JLabel("用户类型",JLabel.CENTER);
		password = new JLabel("用户密码",JLabel.CENTER);
		accoField = new JTextField();
		passField = new JPasswordField();
		logButton = new JButton("登录");
		reSetButton = new JButton("重置");
		registerButton = new JButton("注册");
		jcb = new JComboBox();//下拉框
		jp1 = new JPanel(new GridLayout(3,2,10,20));
		jp2 = new JPanel(new GridLayout(1,3,10,20));
		jp3 = new JPanel();
		

		this.setLayout(null);
		jcb.addItem("管理员");
		jcb.addItem("用户");
		jp1.add(account);
		jp1.add(accoField);
		jp1.add(type);
		jp1.add(jcb);
		jp1.add(password);
		jp1.add(passField);
		jp2.add(logButton);
		jp2.add(reSetButton);
		jp2.add(registerButton);
		jp1.setBounds(0, 0, 280, 200);
		jp2.setBounds(5, 210, 280, 30);
		this.add(jp1);
		this.add(jp2);
		
		
		logButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String acco = accoField.getText();
				String pass = new String(passField.getPassword());
				String type = String.valueOf(jcb.getSelectedItem());//得到下拉选框的值
				int k = userService.log(acco,type,pass);
				user = userService.getUser();

			    if(k == 2) {
			    	visible();
			    	new AdminMainFrame(new User(acco, type, pass));
			    	
			    } else if(k == 1) {
			    	visible();
			    	new UserMainFrame(user);
			    } else if(k == 0) {
			    	new WarningFrame();
			    	accoField.setText("");
					passField.setText("");
			    } 	
			}
		});
		reSetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				accoField.setText("");
				passField.setText("");
			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int k = UserDaoImpl.writeFile(String.valueOf(accoField.getText()), String.valueOf(jcb.getSelectedItem()), String.valueOf(passField.getPassword()));
				if(k == 1) {
					new Register();
				}
			}
		});

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
		    this.setTitle("警告信息");
		    init();
		    this.setSize(250, 110);
		    this.setVisible(true);
		}
		
		public void init() {
			jp1 = new JPanel();
			jp2 = new JPanel();
			button = new JButton("确定");
			icon = new ImageIcon(".\\src\\nuc\\sw\\shopping\\images\\1.jpg");
		    label1 = new JLabel(icon);
		    label1.setSize(30, 30);
		    label2 = new JLabel("登录账号或密码不正确");
			
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
		class Register extends JFrame{
			
			private JLabel label;
			public Register() {
				this.setTitle("注册");
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				label = new JLabel("恭喜您注册成功！现在可以进行登录了！");
				this.add(label,BorderLayout.CENTER);
				this.setSize(250,100);
				this.setVisible(true);
				this.setLocation(30, 100);
			}
		}
	

	public void visible() {
		this.setVisible(false);
	}

		
}
