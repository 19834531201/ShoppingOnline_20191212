package nuc.sw.shopping.exception;

public class NotFindException extends Exception{

	private String bookName;
	public NotFindException(String bookName) {
		// TODO Auto-generated constructor stub
		this.bookName = bookName;
	}
	
	public String getMessage() {
		return "���ﳵ��û���鼮" + bookName;
	}

}
