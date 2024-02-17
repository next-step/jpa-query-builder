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


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
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
        final String name = "new Car";
        final int age = 1;

        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, age);

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                final String result = (String) method.invoke(car);
                logger.info(result);
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void annotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();
        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(car);
            }
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateField() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final int PRIVATE_MODIFIER = 2;
        final String MOCK_NAME = "차이름";
        final int MOCK_PRICE = 1000;
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        for (Field field : carClass.getDeclaredFields()) {
            if (field.getModifiers() == PRIVATE_MODIFIER) {
                field.setAccessible(true);
                if (field.getName().equals("name")) {
                    field.set(car, MOCK_NAME);
                }
                if (field.getName().equals("price")) {
                    field.set(car, MOCK_PRICE);
                }
            }
        }
        assertThat(car.getName()).isEqualTo(MOCK_NAME);
        assertThat(car.getPrice()).isEqualTo(MOCK_PRICE);
    }
}
