package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
        Class<Car> carClass = Car.class;
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(testMethod -> {
                    try {
                        Object result = testMethod.invoke(carClass.newInstance());
                        logger.debug((String) result);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        logger.error(e.getMessage());
                    }
                });
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        Class<Car> carClass = Car.class;
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        Object result = method.invoke(carClass.newInstance());
                        logger.debug((String) result);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                        logger.error(e.getMessage());
                    }
                });
    }

    @Test
    @DisplayName("private field 에 값 할당")
    void privateFieldAccess() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Car> carClass = Car.class;
        Car car = carClass.newInstance();

        Field name = carClass.getDeclaredField("name");
        Field price = carClass.getDeclaredField("price");

        name.setAccessible(true);
        price.setAccessible(true);

        name.set(car, "소나타");
        price.set(car, 100000);

        logger.debug(car.testGetName());
        logger.debug(car.testGetPrice());
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() {
        Class<Car> carClass = Car.class;
        Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() > 0)
                .forEach(constructor -> {
                    try {
                        Car car = (Car) constructor.newInstance("소나타", 10000);
                        logger.debug(car.testGetName());
                        logger.debug(car.testGetPrice());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        logger.error(e.getMessage());
                    }
                });
    }
}
