package persistence;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.study.Car;
import persistence.study.PrintView;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private Class<Car> carClass;

    @BeforeEach
    void before() {
        this.carClass = Car.class;
    }

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        logger.debug(carClass.getName());
    }

    @Test
    @DisplayName("test 로 시작하는 메서드 가져오기")
    void testMethodRun() {
        Method[] methods = carClass.getMethods();

        List<java.lang.String> startsWithTest = Arrays.stream(methods)
            .filter(method -> method.getName().startsWith("test"))
            .map(Method::getName)
            .collect(Collectors.toList());

        logger.debug(startsWithTest.toString());
    }

    @Test
    @DisplayName("execute PrintView annotation")
    void executePrintView() {
        Method[] declaredMethods = carClass.getDeclaredMethods();

        Arrays.stream(declaredMethods)
            .filter(method -> method.isAnnotationPresent(PrintView.class))
            .forEach(this::testAnnotationMethodRun);

    }

    void testAnnotationMethodRun(Method method) {
        try {
            method.invoke(carClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void privateFieldAccess() {
        try {
            final String carName = "my car";
            final int carPrice = 1000;
            Car car = carClass.getDeclaredConstructor().newInstance();

            Field name = car.getClass().getDeclaredField("name");
            Field price = car.getClass().getDeclaredField("price");

            price.setAccessible(true);
            price.set(car, carPrice);

            name.setAccessible(true);
            name.set(car, carName);

            assertAll(
                () -> Assertions.assertThat(car.testGetName()).isEqualTo("test : " + carName),
                () -> Assertions.assertThat(car.testGetPrice()).isEqualTo("test : " + carPrice)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void constructorWithArgs() {
        try {
            final String carName = "my car";
            final int carPrice = 1000;

            Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor(String.class, int.class);
            Car car = declaredConstructor.newInstance(carName, carPrice);

            assertAll(
                () -> Assertions.assertThat(car.testGetName()).isEqualTo("test : " + carName),
                () -> Assertions.assertThat(car.testGetPrice()).isEqualTo("test : " + carPrice)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
