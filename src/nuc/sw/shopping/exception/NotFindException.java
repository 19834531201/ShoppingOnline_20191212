package nuc.sw.shopping.exception;

public class NotFindException extends Exception{

	private String bookName;
	public NotFindException(String bookName) {
		// TODO Auto-generated constructor stub
		this.bookName = bookName;
	}
	
	public String getMessage() {
		return "购物车内没有书籍" + bookName;
	}

}
