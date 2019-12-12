package nuc.sw.shopping.entity;

public class Category {
	private int id;
	private String firstLevel;
	private String secondLevel;
	//setter
	public void setId(int id) {
		this.id = id;
	}
	public void setFirstLevel(String firstLevel) {
		this.firstLevel = firstLevel;
	}
	public void setSecondLevel(String secondLevel) {
		this.secondLevel = secondLevel;
	}
	//getter
	public int getId() {
		return id;
	}
	public String getFirstLevel() {
		return firstLevel;
	}
	public String getSecondLevel() {
		return secondLevel;
	}
	//无参构造函数
	public Category() {}
	//三个参数的构造函数
	public Category(int id, String firstLevel, String secondLevel) {
		this.id = id;
		this.firstLevel = firstLevel;
		this.secondLevel = secondLevel;
	}
	//重写toString方法
	@Override
	public String toString() {
		return firstLevel+">"+secondLevel;
	}

}
