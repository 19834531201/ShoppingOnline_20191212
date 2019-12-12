package nuc.sw.shopping.dao;

/*
 * ��HashSet�����漰���鼮�������޸�ʱ����дһ���������µļ���д���ļ������и��ǣ����ǵ�ԭ�����ļ�
 */
import nuc.sw.shopping.entity.*;

import java.io.*;
import java.util.*;
import nuc.sw.shopping.db.*;

public class BookDaoImpl implements BookDao{
	
	/**
	 * ���γɾ�̬�������ȡ
	 */
	private static Set<Book> books;     
	
	static {
		books = new HashSet<Book>();
		/**
		 * ���û���Ϣ
		 */
		
		BufferedReader br = null;
		FileReader reader = null;
		try {
			reader = new FileReader(DataBaseConfig.BOOK_FILE_PATH);
			br = new BufferedReader(reader);
			
			//����һ�����������ļ�����ת���ɼ�����ܶ���������
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
	 * �����ݿ���д��Ϣ(����Ա���ӵ��鼮��Ϣ���µ��������)
	 */
	
	public static void writeFile(Book book) {
		//���´�������Ϣ���뵽������
		 
    	books.add(book);
    	
		String words = book.getBid() + ":"+ book.getName() + ":" + book.getAuthor()+ ":"+book.getNumber()+":"+book.getCategory().getId() + ":" + book.getCategory().getFirstLevel()+":"+book.getCategory().getSecondLevel()+":"+book.getPrice();
		BufferedWriter writer= null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(DataBaseConfig.BOOK_FILE_PATH,true);
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
	 * ������Ա���������鼮������ʱ,���µļ�������鸲��д��
	 * ǰ�����Ѿ��Ĺ���book����
	 */
	public static void coverFile() {
		//���´�������Ϣ���뵽������
			
		String words = null;
		BufferedWriter writer= null;
		FileWriter fileWriter = null;
		FileWriter fileWriter1 = null;
		
		//�Ȱ��ļ����
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
			    //һ��Ҫ�ǵ�close���ر�д�룬�����޷�����д�������ݣ��漰���ļ��Ķ��ùر�			
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
	//��id��ѯ
	@Override
	public  Book queryById(String id) {
		Book book = null;//�ǵó�ʼ��
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
		Book book = null;//�ǵó�ʼ��
		for(Book b : books) {
			if(name.equals(b.getName())) {
				book = b;
				break;
			}
		}
		return book;	
	}
    
	/**
	 * �ж�����ӵ��鼮�����ݿ����Ƿ����
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
	 * ɾ���鼮
	 */
	public void deleteBook(Book book) {
		books.remove(book);
		coverFile();
	}

	/**
	 * ��ȡ���µ�books����
	 * @param newBooks
	 */
	public static void getNewBooks(Set<Book> newBooks) {
		// TODO Auto-generated method stub
		books = newBooks;
	}


}
