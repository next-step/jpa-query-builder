package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(x -> x.getName().contains("test"))
                .forEach(x -> {
            try {
                x.invoke(car);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> carClass = Car.class;
        Car car = carClass.getConstructor().newInstance();
        Arrays.stream(carClass.getDeclaredMethods())
            .filter( x -> x.isAnnotationPresent(PrintView.class))
            .forEach(x -> {
                try {
                    x.invoke(car);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @Test
    public void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<Car> clazz = Car.class;
        logger.debug(clazz.getName());

        String name = "test_name";
        int price = 0;

        Car car = clazz.getConstructor().newInstance();
        Field nameField = clazz.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(car, name);
        assertThat(car.getName()).isEqualTo(name);

        Field priceField = clazz.getDeclaredField("price");
        priceField.setAccessible(true);
        priceField.set(car, price);
        assertThat(car.getPrice()).isEqualTo(price);
    }

    @Test
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> clazz = Car.class;
        String name = "test_name";
        int price = 0;

        Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                .filter(x -> 0 < x.getParameterCount())
                .findFirst().orElseThrow();
        Car car = (Car) constructor.newInstance(name, price);

        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }
}