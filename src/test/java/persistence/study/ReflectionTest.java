package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    Class<Car> carClass = Car.class;

    @Test
    @DisplayName("car 객체 정보 가져오기")
    void showClass() {
        logger.debug(carClass.getName());
    }


    @Test
    @DisplayName("car 객체의 이름이 test 로 시작하는 메서드만 실행하기")
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Car car = carClass.getDeclaredConstructor(String.class, int.class).newInstance("test", 123);
        final String targetMethodNamePrefix = "test";

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith(targetMethodNamePrefix))
                .forEach(method -> {
                    try {
                        String result = (String) method.invoke(car);
                        assertThat(result).startsWith("test:");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("car 객체의 @PrintView 어노테이션이 붙은 메서드만 실행하기")
    public void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Car car = carClass.getDeclaredConstructor().newInstance();

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        System.setOut(new PrintStream(outputStream));
                        method.invoke(car);
                        assertThat(outputStream.toString()).isEqualTo("자동차 정보를 출력합니다.\n");
                    } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        System.setOut(System.out);
    }

    @Test
    @DisplayName("car 객체의 private 필드에 접근하기")
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        final String name = "testCar";
        final int price = 10_000;
        Car car = carClass.getDeclaredConstructor().newInstance();

        Field nameField = carClass.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(car, name);

        Field priceField = carClass.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(car, price);

        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String name = "testCar";
        final int price = 10_000;
        Car car = carClass.getDeclaredConstructor(String.class, int.class).newInstance(name, price);

        assertThat(car).isNotNull();
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }
}

