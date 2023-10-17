package persistence.study;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);


    @Test
    @DisplayName("요구사항 1 - Car 클래스 정보(모든 필드, 생성자, 메소드) 출력")
    void showClass() {
        Class<Car> carClass = Car.class;

        logger.debug("Car 클래스의 모든 필드 {}",  Arrays.toString(carClass.getDeclaredFields()));
        logger.debug("Car 클래스의 생성자 {}",  Arrays.toString(carClass.getDeclaredFields()));
        logger.debug("Car 클래스의 메소드 {}",  Arrays.toString(carClass.getDeclaredFields()));
    }

    @Test
    @DisplayName("요구사항 2 - test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        Car car = carClass.getDeclaredConstructor().newInstance();

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(it-> it.getName().startsWith("test"))
                .forEach(it -> executeMethodInvoke(it, car));


        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                logger.debug("메소드 호출 {}", method.invoke(car));
            }
        }
    }

    private static void executeMethodInvoke(Method method, Car car) {
        try {
            logger.debug("메소드 호출 {}", method.invoke(car));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        Car car = carClass.getDeclaredConstructor().newInstance();

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(it-> it.isAnnotationPresent(PrintView.class))
                .forEach(it -> executeMethodInvoke(it, car));
    }

    @Test
    @DisplayName("요구사항 4 - private field에 값 할당")
    void privateFieldAccess() throws Exception {
        //given
        final String carName = "소나타";
        final int price = 10;

        //when
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        for (Field field : carClass.getDeclaredFields()) {
            if (field.getName().equals("name")) {
                field.setAccessible(true);
                field.set(car, carName);
            }
            if (field.getName().equals("price")) {
                field.setAccessible(true);
                field.set(car, price);
            }
        }

        //then
        assertSoftly((it -> {
            it.assertThat(car.testGetName()).isEqualTo("test : " + carName);
            it.assertThat(car.testGetPrice()).contains("test : " + price);
        }));
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        //given
        final String carName = "소나타";
        final int price = 10;

        //when
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor(String.class, int.class).newInstance(carName, 10);

        //then
        assertSoftly((it -> {
            it.assertThat(car.testGetName()).isEqualTo("test : " + carName);
            it.assertThat(car.testGetPrice()).contains("test : " + price);
        }));
    }

}
