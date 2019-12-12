package nuc.sw.shopping.entity;

public class Book {
	private String bid;
	private String name;
	private String author;
	private int number;
	private Category category;
	private double price;
	
	public Book(String bid, String name, String author, int number, Category category,double price) {
		super();
		this.bid = bid;
		this.name = name;
		this.author = author;
		this.number = number;
		this.category = category;
		this.price = price;
	}
	
	//setter and getter
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	
	public Book() {
		super();
	}
	//
	public String toString() {
		return "书籍编号 : "+bid+" | 书籍名称 : "+name+" | 目录"+category.toString()+" | 书籍库存"+number;		
	}
	

}



