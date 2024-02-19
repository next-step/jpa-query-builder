package persistence.study;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void showCarClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        // 필드
        Field[] fields = carClass.getDeclaredFields();
        System.out.println("필드:");
        for (Field field : fields) {
            System.out.println(field);
        }

        // 생성자
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        System.out.println("생성자:");
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }

        // 메서드
        Method[] methods = carClass.getDeclaredMethods();
        System.out.println("메서드");
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    @Test
    void test_로_시작하는_메소드_실행() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Car> carClass = Car.class;

        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Car car = (Car) findConstructorByArgumentCount(constructors, 0).get()
                .newInstance();

        Method[] methods = carClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                method.invoke(car);
            }
        }
    }

    @Test
    void PrintView_애노테이션_메소드_실행() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;

        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Car car = (Car) findConstructorByArgumentCount(constructors, 0).get()
                .newInstance();

        Method[] methods = carClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(car);
            }
        }
    }

    @Test
    void private_field에_값_할당() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Car car = (Car) findConstructorByArgumentCount(constructors, 0).get()
                .newInstance();

        // 필드
        Field[] fields = carClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("name")) {
                field.set(car, "소나타");
            } else if (field.getName().equals("price")) {
                field.set(car, 123);
            }
        }

        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 소나타"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 123")
        );
    }

    @Test
    void 인자를_가진_생성자의_인스턴스_생성() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Constructor<?> constructor = findConstructorByArgumentCount(constructors, 2).get();
        Car car = (Car) constructor.newInstance("소나타", 123);

        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 소나타"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 123")
        );
    }

    private static Optional<Constructor<?>> findConstructorByArgumentCount(Constructor<?>[] constructors, int count) {
        return Arrays.stream(constructors)
                .filter(constructor -> constructor.getParameterCount() == count)
                .findFirst();
    }
}
