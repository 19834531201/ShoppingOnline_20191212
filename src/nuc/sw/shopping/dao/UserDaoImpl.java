package nuc.sw.shopping.dao;

import nuc.sw.shopping.db.*;
import nuc.sw.shopping.entity.*;
import nuc.sw.shopping.exception.BookException;
import java.io.*;
import java.util.*;


public class UserDaoImpl implements UserDao {



	/**
	 * 从这里开始修改
	 */
	private static Set<User> users;
	private ShoppingCart orderCart;
	private Set<User> orderUser;//存放订单
	private User userOrder;//每一个有订单的用户
	
	public UserDaoImpl() {
		orderCart = new ShoppingCart();
		orderUser = new HashSet<User>();
		getOrderFromFile();//让orderUser初始化
	}
	//如果以后出现了问题，就把这里的static代码块换成读文件的静态函数
	static {
		users = new HashSet<User>();
		/**
		 * 读用户信息
		 */
		BufferedReader br = null;
		FileReader reader = null;
		try {
			reader = new FileReader(DataBaseConfig.USER_FILE_PATH);
			br = new BufferedReader(reader);
			
			//建立一个对象，它把文件内容转换成计算机能读懂的语言
			String words = null;
			String[] infoStrings = null;
			Book book = null;
			int i = 3;
			int k =1;
			BookDaoImpl bookDaoImpl = null;
			while(( words= br.readLine()) != null) {
				infoStrings = null;
				ShoppingCart carts = new ShoppingCart();//必须每次新建购物车
				 i = 3;
				infoStrings = words.split(":");
//				System.out.println("用户名"+infoStrings[0]);
				if(infoStrings.length == 3) {
				    users.add(new User(infoStrings[0],infoStrings[1],infoStrings[2]));			
				} else {
					while(i < infoStrings.length) {
						bookDaoImpl = new BookDaoImpl();
						book = bookDaoImpl.queryById(infoStrings[i]);
						carts.add(book,Integer.parseInt(infoStrings[i + 1]));
//						System.out.println("书籍名"+infoStrings[i]);
//						System.out.println("数量" +infoStrings[i + 1]);//这里读取有问题
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
	 * 往数据库里写信息,往数据库里补充信息,注册的时候用
	 */
//	//将新创建的信息加入到集合中
//			User user = new User(account, type ,password);
//	    	users.add(user);
	public static int writeFile(String account,String type,String password) {
		
//    	Object[] userObjects = users.toArray();
//    	User user = (User)userObjects[userObjects.length - 1];//获取到新加入的用户
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
			//一定要记得close，关闭写入，否则无法看到写入后的内容，涉及到文件的都得关闭			
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
	 * 覆盖文件，将用户的购物车信息写入文件
	 */
	public static void coverFile(User newUser) {
		
		Object[] users2 = users.toArray();
		

		//更新用户的购物车信息
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
			    //一定要记得close，关闭写入，否则无法看到写入后的内容，涉及到文件的都得关闭			
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
	 * userOrder为多个用户的集合，每一个用户有一个购物车
	 * 得到OrderCart的集合,将userAcco直接写到文件每一行的开头就行
	 * @param book
	 * @param number
	 */
	public void setOrder(Book book,int number,User user) {
		
		User oldUser = null;//为过去的用户
		Book newBook = null;
		int oldNumber = 0;
		userOrder = user;
		Boolean flag = false;//flag为false则订单记录里没有这个用户
		Boolean flag1 = false;//flag1为用户之前购买过这本书
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
	 * 每一次都重新写集合
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
	 * 从文件中得到订单
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
				ShoppingCart carts = new ShoppingCart();//必须每次新建购物车
				 i = 3;
				infoStrings = words.split(":");
//				System.out.println("用户名"+infoStrings[0]);
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
 