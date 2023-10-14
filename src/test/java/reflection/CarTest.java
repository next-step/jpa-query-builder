package reflection;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class CarTest {

    private static final Logger logger = LoggerFactory.getLogger(CarTest.class);

    @Test
    void required1() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
    }

    @Test
    void required2() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<?> constructor = carClass.getDeclaredConstructors()[0];

        Car car = (Car) constructor.newInstance();

        List<Method> testMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().contains("test"))
                .collect(Collectors.toUnmodifiableList());

        testMethods.forEach(method -> {
            try {
                method.invoke(car);
                logger.info("Invoke Method name: {}", method.getName());
            } catch (Exception e) {
                logger.error("not Invoke Method : {}", method.getName());
            }
        });
    }

    @Test
    void required3() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<?> constructor = carClass.getDeclaredConstructors()[0];

        Car car = (Car) constructor.newInstance();

        List<Method> hasPrintViewAnnotationMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toUnmodifiableList());

        hasPrintViewAnnotationMethods.forEach(method -> {
            try {
                method.invoke(car);

                logger.info("Invoke Method name: {}", method.getName());
            } catch (Exception e) {
                logger.error("not Invoke Method : {}", method.getName());
            }
        });
    }

    @Test
    void required4() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<?> constructor = carClass.getDeclaredConstructors()[0];

        Car car = (Car) constructor.newInstance();

        Field nameField = carClass.getDeclaredField("name");

        nameField.setAccessible(true);

        String name = "소나타";
        nameField.set(car, name);

        assertThat(car.testGetName()).contains(name);
    }


    @Test
    void required5() throws Exception {
        String name = "소나타";
        int price = 1000;

        Class<Car> carClass = Car.class;

        Constructor<?> argConstructor = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    if (parameterTypes.length != 2) {
                        return false;
                    }

                    return parameterTypes[0] == String.class && parameterTypes[1] == int.class;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 생성자가 없습니다."));


        Car car = (Car) argConstructor.newInstance("소나타", 1000);

        assertAll(
                () -> assertThat(car.testGetName()).contains(name),
                () -> assertThat(car.testGetPrice()).contains(String.valueOf(price))
        );
    }

}
