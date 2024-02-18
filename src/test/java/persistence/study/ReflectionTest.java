package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        final Class<Car> carClass = Car.class;
        // 패키지+클래스 이름
        logger.debug(carClass.getName());
        // 모든 필드 목록
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));
        // 모든 생성자 반환
        logger.debug(Arrays.toString(carClass.getDeclaredConstructors()));
        // 모든 메서드 반환
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        for (final Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                final String result = (String) method.invoke(car);
                logger.debug(result);
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();
        for (final Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(car);
            }
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();
        final String name = "Sonata";
        final int price = 1000;

        for (final Field field : carClass.getDeclaredFields()) {
            if (field.getName().equals("name")) {
                field.setAccessible(true);
                field.set(car, name);
            }
            if (field.getName().equals("price")) {
                field.setAccessible(true);
                field.set(car, price);
            }
        }

        assertAll(
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final String name = "Sonata";
        final int price = 1000;

        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        assertAll(
                () -> assertThat(car).isInstanceOf(Car.class),
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }
}
