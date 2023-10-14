package persistence.study;

public class Car {

	public static final String TEST_METHOD_PREFIX = "test : ";

	private String name;
	private int price;

	public Car() {

	}

	public Car(String name, int price) {
		this.name = name;
		this.price = price;
	}

	@PrintView
	public void printView() {
		System.out.println("자동차 정보를 출력 합니다.");
	}

	public String testGetName() {
		return TEST_METHOD_PREFIX + name;
	}

	public String testGetPrice() {
		return TEST_METHOD_PREFIX + price;
	}
}

