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
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        logger.debug(carClass.getName());
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));
        logger.debug(Arrays.toString(carClass.getDeclaredConstructors()));
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));
    }

    @Test
    @DisplayName("test 로 시작하는 메소드 실행하기")
    void testMethodRun() {
        // given
        Class<Car> carClass = Car.class;

        // when
        List<Object> result = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(testMethod -> invokeMethod(testMethod, carClass))
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result).containsAll(List.of("test : null", "test : 0"))
        );
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        // given
        Class<Car> carClass = Car.class;

        // when
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> invokeMethod(method, carClass));
    }

    @Test
    @DisplayName("private field 에 값 할당")
    void privateFieldAccess() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        // given
        Class<Car> carClass = Car.class;
        Car car = carClass.newInstance();

        // when
        Field name = carClass.getDeclaredField("name");
        Field price = carClass.getDeclaredField("price");

        name.setAccessible(true);
        price.setAccessible(true);

        name.set(car, "소나타");
        price.set(car, 100000);

        // then
        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 소나타"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 100000")
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        Class<Car> carClass = Car.class;

        // when
        Constructor<?> constructorWithArgs = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .findFirst().get();

        Car car = (Car) constructorWithArgs.newInstance("소나타", 100000);

        // then
        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 소나타"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 100000")
        );
    }

    private Object invokeMethod(Method method, Class<Car> car) {
        try {
            return method.invoke(car.newInstance());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
