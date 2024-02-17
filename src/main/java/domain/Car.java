package domain;

import annotation.PrintView;

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
        return name;
    }

    public int getPrice() {
        return price;
    }

    @PrintView
    public void printView() {
        System.out.println("자동차 정보를 출력 합니다.");
    }

    public String testGetName() {
        return "test : " + name;
    }

    public String testGetPrice() {
        return "test : " + price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

