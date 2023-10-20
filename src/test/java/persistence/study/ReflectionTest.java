package persistence.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import domain.Car;
import annotation.PrintView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
    }

    @Test
    @DisplayName("Car 객체의 test로 시작하는 메소드를 실행하기")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(x -> x.getName().contains("test"))
                .forEach(x -> {
                    try {
                        x.invoke(car);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("Car 객체의 @PrintView 설정되어 있는 메소드를 실행하기")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();
        Arrays.stream(carClass.getDeclaredMethods())
                .filter( x -> x.isAnnotationPresent(PrintView.class))
                .forEach(x -> {
                    try {
                        x.invoke(car);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("Car 객체의 private 필드에 값을 할당 후 값을 확인하기")
    public void privateFieldAccess() throws Exception {
        Class<Car> clazz = Car.class;
        logger.debug(clazz.getName());

        String name = "test_name";
        int price = 0;

        Car car = clazz.getConstructor().newInstance();
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(car, name);

        Field priceField = clazz.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(car, price);

        Assertions.assertAll(
                () -> assertTrue(car.getName().equals(name)),
                () -> assertTrue(car.getPrice() == price)
        );
    }

    @Test
    @DisplayName("Reflection API를 활용해 Car 인스턴스를 생성하기")
    void constructorWithArgs() throws Exception {
        Class<Car> clazz = Car.class;
        String name = "test_name";
        int price = 0;

        Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(x -> 0 < x.getParameterCount())
                .findFirst().orElseThrow();
        Car car = (Car) constructor.newInstance(name, price);

        Assertions.assertAll(
                () -> assertTrue(car.getName().equals(name)),
                () -> assertTrue(car.getPrice() == price)
        );
    }

}
