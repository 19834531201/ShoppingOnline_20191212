package nuc.sw.shopping.entity;

public class User {

	private ShoppingCart cart;
	private String account;
	private String password;
	private String type;//用户还是管理员

	public User(String account, String type,String password) {
		super();
		this.account = account;
		this.password = password;
		this.type = type;
		this.cart = new ShoppingCart();

	}
	
	/**
	 * 用户的构造方法,自带购物车
	 */
	public User(String account,String type,String password,ShoppingCart cart) {
		super();
		this.account = account;
		this.password = password;
		this.type = type;
		this.cart = cart;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 判断是否为用户，不是管理员
	 */
	public boolean isUser() {
		return this.type.equals("用户");
	}

	public ShoppingCart getShoppingCart() {
		// TODO Auto-generated method stub
		return null;
	}

}
