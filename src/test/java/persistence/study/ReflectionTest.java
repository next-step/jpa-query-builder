package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
    }

    @Test
    @DisplayName("test로 시작하는 메서드 실행하기")
    void testMethodRun() throws Exception {
        Method[] methods = Car.class.getDeclaredMethods();
        Car car = Car.class.getDeclaredConstructor().newInstance();

        Arrays.stream(methods)
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        String result = (String) method.invoke(car);
                        assertThat(result).isNotBlank();
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메서드 실행")
    void testAnnotationMethodRun() throws Exception {
        Method[] methods = Car.class.getDeclaredMethods();
        Car car = Car.class.getDeclaredConstructor().newInstance();

        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(car);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws Exception {
        Car car = new Car("toto", 10000);
        String carName = "테슬라";
        int carPrice = 10_000_000;

        Field name = Car.class.getDeclaredField("name");
        Field price = Car.class.getDeclaredField("price");

        name.setAccessible(true);
        price.setAccessible(true);

        name.set(car, carName);
        price.set(car, carPrice);
        
        assertThat(car.testGetName()).isEqualTo("test : " + carName);
        assertThat(car.testGetPrice()).isEqualTo("test : " + carPrice);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> clazz = Car.class;
        Constructor<Car> constructor = clazz.getConstructor(String.class, int.class);
        String carName = "테슬라";
        int carPrice = 10_000_000;

        Car newCar = constructor.newInstance(carName, carPrice);

        assertThat(newCar.testGetName()).isEqualTo("test : " + carName);
        assertThat(newCar.testGetPrice()).isEqualTo("test : " + carPrice);
    }
}
