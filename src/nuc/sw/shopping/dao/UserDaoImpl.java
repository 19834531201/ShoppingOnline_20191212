package nuc.sw.shopping.dao;

import nuc.sw.shopping.db.*;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.exception.BookException;
import java.io.*;
import java.util.*;


public class UserDaoImpl implements UserDao {



	/**
	 * �����￪ʼ�޸�
	 */
	private static Set<User> users;
	private ShoppingCart orderCart;
	private Set<User> orderUser;//��Ŷ���
	private User userOrder;//ÿһ���ж������û�
	
	public UserDaoImpl() {
		orderCart = new ShoppingCart();
		orderUser = new HashSet<User>();
		getOrderFromFile();//��orderUser��ʼ��
	}
	//����Ժ���������⣬�Ͱ������static����黻�ɶ��ļ��ľ�̬����
	static {
		users = new HashSet<User>();
		/**
		 * ���û���Ϣ
		 */
		BufferedReader br = null;
		FileReader reader = null;
		try {
			reader = new FileReader(DataBaseConfig.USER_FILE_PATH);
			br = new BufferedReader(reader);
			
			//����һ�����������ļ�����ת���ɼ�����ܶ���������
			String words = null;
			String[] infoStrings = null;
			Book book = null;
			int i = 3;
			int k =1;
			BookDaoImpl bookDaoImpl = null;
			while(( words= br.readLine()) != null) {
				infoStrings = null;
				ShoppingCart carts = new ShoppingCart();//����ÿ���½����ﳵ
				 i = 3;
				infoStrings = words.split(":");
//				System.out.println("�û���"+infoStrings[0]);
				if(infoStrings.length == 3) {
				    users.add(new User(infoStrings[0],infoStrings[1],infoStrings[2]));			
				} else {
					while(i < infoStrings.length) {
						bookDaoImpl = new BookDaoImpl();
						book = bookDaoImpl.queryById(infoStrings[i]);
						carts.add(book,Integer.parseInt(infoStrings[i + 1]));
//						System.out.println("�鼮��"+infoStrings[i]);
//						System.out.println("����" +infoStrings[i + 1]);//�����ȡ������
//						
						i += 2;
					}
					users.add(new User(infoStrings[0],infoStrings[1],infoStrings[2],carts));

				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}

	}
	

	/**
	 * �����ݿ���д��Ϣ,�����ݿ��ﲹ����Ϣ,ע���ʱ����
	 */
//	//���´�������Ϣ���뵽������
//			User user = new User(account, type ,password);
//	    	users.add(user);
	public static int writeFile(String account,String type,String password) {
		
//    	Object[] userObjects = users.toArray();
//    	User user = (User)userObjects[userObjects.length - 1];//��ȡ���¼�����û�
		int flag = 0;
		User user = new User(account, type ,password);
    	users.add(user);
    	String words = user.getAccount() + ":"+ user.getType() + ":" + user.getPassword();
    	String word = null;
    	
    	if(user.isUser()) {
    		for (Map.Entry<Book, Integer> entry: user.getCart().entrySet()) {
    			word = ":" + entry.getKey().getBid() + ":" +entry.getValue();
    			words += word;
    		}
    		words = user.getAccount() + ":"+ user.getType() + ":" + user.getPassword() + words; 
    	}
    	BufferedWriter writer= null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(DataBaseConfig.USER_FILE_PATH,true);
			writer = new BufferedWriter(fileWriter);
			writer.write(words);
			writer.newLine();
			flag = 1;
			//һ��Ҫ�ǵ�close���ر�д�룬�����޷�����д�������ݣ��漰���ļ��Ķ��ùر�			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(flag ==1 ) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * �����ļ������û��Ĺ��ﳵ��Ϣд���ļ�
	 */
	public static void coverFile(User newUser) {
		
		Object[] users2 = users.toArray();
		

		//�����û��Ĺ��ﳵ��Ϣ
		for(int i = 0; i < users.size();i ++) {
			if(newUser.getAccount().equals(((User)users2[i]).getAccount()) && newUser.getType().equals(((User)users2[i]).getType())){
				users.remove(users2[i]);
				users.add(newUser);
				break;
			}
		}

//		for(int b : newUser.getCart().values()) {
//		System.out.println(b);
//	    }
    	BufferedWriter writer= null;
		FileWriter fileWriter = null;
		String words = null,word = null,words2 = "";
		
		try {
			fileWriter = new FileWriter(DataBaseConfig.USER_FILE_PATH);
			writer = new BufferedWriter(fileWriter);
			writer.write("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for(User user : users) {
			words2 = "";
			word = "";
			words = user.getAccount() + ":"+ user.getType() + ":" + user.getPassword();
	    	if(user.isUser()) {
	    	
	    		for (Map.Entry<Book, Integer> entry: user.getCart().entrySet()) {
	    			word = ":" + entry.getKey().getBid() + ":" +entry.getValue();
	    			words2 += word;
	    		}
	    		words = user.getAccount() + ":"+ user.getType() + ":" + user.getPassword() + words2; 
	    	}
		    try {
			    fileWriter = new FileWriter(DataBaseConfig.USER_FILE_PATH,true);
			    writer = new BufferedWriter(fileWriter);
			    writer.write(words);
			    writer.newLine();
			    //һ��Ҫ�ǵ�close���ر�д�룬�����޷�����д�������ݣ��漰���ļ��Ķ��ùر�			
		    } catch(Exception e) {
		     	e.printStackTrace();
		    } finally {
			    if(writer != null) {
			    	try {
				    	writer.close();
				    	fileWriter.close();
				    } catch (IOException e) {
				    	// TODO Auto-generated catch block
				    	e.printStackTrace();
				     }
			    }
	          }
		}
	}
	@Override
	public Set<User> getUser() {
		// TODO Auto-generated method stub
//		for(User u : users) {
//			for(int i : u.getCart().values()) {
//				System.out.println(i);
//			}
//		}
		return users;
	}
	
	/**
	 * userOrderΪ����û��ļ��ϣ�ÿһ���û���һ�����ﳵ
	 * �õ�OrderCart�ļ���,��userAccoֱ��д���ļ�ÿһ�еĿ�ͷ����
	 * @param book
	 * @param number
	 */
	public void setOrder(Book book,int number,User user) {
		
		User oldUser = null;//Ϊ��ȥ���û�
		Book newBook = null;
		int oldNumber = 0;
		userOrder = user;
		Boolean flag = false;//flagΪfalse�򶩵���¼��û������û�
		Boolean flag1 = false;//flag1Ϊ�û�֮ǰ������Ȿ��
		for(User u : orderUser) {
			if(user.getAccount().equals(u.getAccount())) {
				flag = true;
				oldUser = u;
				userOrder = u;
				break;
			}
		}
		if(flag == true) {
			orderUser.remove(oldUser);
			for(Map.Entry<Book, Integer> map : userOrder.getCart().entrySet()) {
				if(book.getBid().equals(map.getKey().getBid())) {
					oldNumber = map.getValue();
					userOrder.getCart().remove(map.getKey());
					flag1 =true;
					break;
				}
			}
			if(flag1 == true) {
				try {
					userOrder.getCart().add(book,oldNumber + number);
				} catch (BookException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					userOrder.getCart().add(book, number);
				} catch (BookException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		} else {
			try {
				userOrder.getCart().add(book, number);
			} catch (BookException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		orderUser.add(userOrder);
		writeOrderToFile();
		
	}
	
	/**
	 * ÿһ�ζ�����д����
	 */
	public void writeOrderToFile() {
		FileWriter fileWriter = null;
		BufferedWriter bW = null;
		try {
			fileWriter = new FileWriter(DataBaseConfig.ORDER_FILE_PATH);
			bW = new BufferedWriter(fileWriter);
			bW.write("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bW != null) {
				try {
					bW.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(User user : orderUser) {
		    String words = user.getAccount()+":"+user.getType()+":"+user.getPassword();
		    try {
		    	fileWriter = new FileWriter(DataBaseConfig.ORDER_FILE_PATH,true);
		    	bW = new BufferedWriter(fileWriter);
		    	for(Map.Entry<Book, Integer> map : user.getCart().entrySet()) {
		    		words += "" + ":" + map.getKey().getBid() +":" +map.getValue();
		    	}
		    	bW.write(words);
		    	bW.newLine();
		    } catch (IOException e) {
		    	// TODO Auto-generated catch block
		    	e.printStackTrace();
		    } finally {
		    	if(bW != null) {
		    		try {
		    			bW.close();
		    		} catch (IOException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    	}
		    	if(fileWriter != null) {
		    		try {
						fileWriter.close();
		    		} catch (IOException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
		    	}
		    }
		}
		
	}
	
	/**
	 * ���ļ��еõ�����
	 */
	public Set<User> getOrderFromFile() {
		FileReader fileReader = null;
		BufferedReader br = null;
		try {
			fileReader = new FileReader(DataBaseConfig.ORDER_FILE_PATH);
			br = new BufferedReader(fileReader);

			String words = null;
			String[] infoStrings = null;
			Book book = null;
			int i = 3;
			int k =1;
			BookDaoImpl bookDaoImpl = null;
			while(( words= br.readLine()) != null) {
				infoStrings = null;
				ShoppingCart carts = new ShoppingCart();//����ÿ���½����ﳵ
				 i = 3;
				infoStrings = words.split(":");
//				System.out.println("�û���"+infoStrings[0]);
				while(i < infoStrings.length) {
					bookDaoImpl = new BookDaoImpl();
					book = bookDaoImpl.queryById(infoStrings[i]);
					carts.add(book,Integer.parseInt(infoStrings[i + 1]));						
					i += 2;
				}
				orderUser.add(new User(infoStrings[0],infoStrings[1],infoStrings[2],carts));

			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		return orderUser;
	}
	
	public Set<User> getOrder(){
		return orderUser;
	}
	
}
 