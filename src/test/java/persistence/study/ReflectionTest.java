package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        for (Field field : carClass.getDeclaredFields()) {
            logger.debug(field.getName());
        }

        for (Constructor<?> constructor : carClass.getDeclaredConstructors()) {
            logger.debug(constructor.getName());
        }

        for (Method method : carClass.getDeclaredMethods()) {
            logger.debug(method.getName());
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                method.setAccessible(true);
                logger.debug((String) method.invoke(car));
                assertThat((String) method.invoke(car)).matches("^test.*"); ;
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();

        PrintStream originalStream = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.setAccessible(true);
                method.invoke(car);
                assertThat(outputStream.toString()).contains("자동차 정보를 출력 합니다.");
            }
        }
        System.setOut(originalStream);
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();
        String name = "테슬라";
        int price = 99999;

        for (Field field : carClass.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(car, field.getType() == int.class ? price : name);
        }

        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> carClass = Car.class;
        String name = "테슬라";
        int price = 99999;
        Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }

}
