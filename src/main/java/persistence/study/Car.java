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

    @PrintView
    public void printView() {
        System.out.println("자동차 정보를 출력 합니다.");
    }

    public java.lang.String testGetName() {
        return "test : " + name;
    }

    public java.lang.String testGetPrice() {
        return "test : " + price;
    }
}

