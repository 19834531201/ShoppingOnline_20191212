package nuc.sw.shopping.dao;

/*
 * 用HashSet，当涉及到书籍数量的修改时，再写一个方法将新的集合写入文件，进行覆盖，覆盖掉原来的文件
 */
import nuc.sw.shopping.entity.*;

import java.io.*;
import java.util.*;
import nuc.sw.shopping.db.*;

public class BookDaoImpl implements BookDao{
	
	/**
	 * 修饰成静态量方便获取
	 */
	private static Set<Book> books;     
	
	static {
		books = new HashSet<Book>();
		/**
		 * 读用户信息
		 */
		
		BufferedReader br = null;
		FileReader reader = null;
		try {
			reader = new FileReader(DataBaseConfig.BOOK_FILE_PATH);
			br = new BufferedReader(reader);
			
			//建立一个对象，它把文件内容转换成计算机能读懂的语言
			String words = null;    
			Category category = null;
			String[] infoStrings;
			while(( words= br.readLine()) != null) {
				infoStrings = words.split(":");
				category = new Category(Integer.parseInt(infoStrings[4]),infoStrings[5],infoStrings[6]);
				books.add(new Book(infoStrings[0],infoStrings[1],infoStrings[2],Integer.parseInt(infoStrings[3]),category,Double.parseDouble(infoStrings[7])));
			}
										
								
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * 往数据库里写信息(管理员增加的书籍信息，新的种类的书)
	 */
	
	public static void writeFile(Book book) {
		//将新创建的信息加入到集合中
		 
    	books.add(book);
    	
		String words = book.getBid() + ":"+ book.getName() + ":" + book.getAuthor()+ ":"+book.getNumber()+":"+book.getCategory().getId() + ":" + book.getCategory().getFirstLevel()+":"+book.getCategory().getSecondLevel()+":"+book.getPrice();
		BufferedWriter writer= null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(DataBaseConfig.BOOK_FILE_PATH,true);
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
	
	/**
	 * 当管理员增加已有书籍的数量时,将新的集合类的书覆盖写入
	 * 前提是已经改过了book集合
	 */
	public static void coverFile() {
		//将新创建的信息加入到集合中
			
		String words = null;
		BufferedWriter writer= null;
		FileWriter fileWriter = null;
		FileWriter fileWriter1 = null;
		
		//先把文件清空
		try {
			fileWriter1 = new FileWriter(DataBaseConfig.BOOK_FILE_PATH);
			fileWriter1.write("");			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(fileWriter1 != null) {
				try {
					fileWriter1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		for(Book book : books) {
			words = book.getBid() + ":"+ book.getName() + ":" + book.getAuthor()+ ":"+book.getNumber()+":"+book.getCategory().getId() +":"+ book.getCategory().getFirstLevel()+":"+book.getCategory().getSecondLevel()+":"+book.getPrice();
		    try {
			    fileWriter = new FileWriter(DataBaseConfig.BOOK_FILE_PATH,true);
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
	
	public void renewCart() {
		
	}

	
	public static Set<Book> getBooks(){
		return books;
	}
	//用id查询
	@Override
	public  Book queryById(String id) {
		Book book = null;//记得初始化
		for(Book b : books) {
			if(id.equals(b.getBid())) {
				book = b;
				break;
			}
		}
		return book;	
	}

	@Override
	public Set<Book> queryBooks() {
		// TODO Auto-generated method stub
		return books;
	}
	
	public Book queryByName(String name) {
		Book book = null;//记得初始化
		for(Book b : books) {
			if(name.equals(b.getName())) {
				book = b;
				break;
			}
		}
		return book;	
	}
    
	/**
	 * 判断所添加的书籍在数据库中是否存在
	 * @return
	 */
	public boolean isExit(String id) {
		Book book = null;
		for(Book b : books) {
			if(id.equals(b.getBid())) {
				book = b;
				return true;
		    }
	   }
	   return false;
    }
	
	/**
	 * 删除书籍
	 */
	public void deleteBook(Book book) {
		books.remove(book);
		coverFile();
	}

	/**
	 * 获取最新的books集合
	 * @param newBooks
	 */
	public static void getNewBooks(Set<Book> newBooks) {
		// TODO Auto-generated method stub
		books = newBooks;
	}


}
