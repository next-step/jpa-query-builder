package persistence.study;

public class Car {
    private String name;
    private int price;

    public Car() {

    }

    public Car(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    @PrintView
    public void printView() {
        System.out.printf("자동차 정보를 출력합니다. name : %s, price : %d\n", name, price);
    }

    public String testGetName() {
        return "test : " + name;
    }

    public String testGetPrice() {
        return "test : " + price;
    }
}
