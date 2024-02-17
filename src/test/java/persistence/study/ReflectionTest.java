package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        printInformation(carClass.getDeclaredFields());
        printInformation(carClass.getConstructors());
        printInformation(carClass.getDeclaredMethods());
    }

    private <T> void printInformation(T[] data) {
        Arrays.stream(data).forEach(System.out::println);
    }

    @Test
    void testMethodRun() {
        Car car = new Car("소나타", 10000);
        Class<Car> carClass = Car.class;

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        System.out.println(method.invoke(car));
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                });
    }

    @Test
    void testAnnotationMethodRun() {
        Car car = new Car("소나타", 10000);
        Class<Car> carClass = Car.class;

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(car);
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                });
    }

    @Test
    void privateFieldAccess() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String name = "소나타";
        Integer price = 10_000;
        Class<Car> carClass = Car.class;

        Field nameField = setFieldPublic(carClass, "name");
        Field priceField = setFieldPublic(carClass, "price");
        Car car = carClass.getDeclaredConstructor().newInstance();
        nameField.set(car, name);
        priceField.set(car, price);

        assertThat(car.testGetName()).contains(name);
        assertThat(car.testGetPrice()).contains(String.valueOf(price));

    }

    private Field setFieldPublic(Class<Car> carClass, String fieldName) throws NoSuchFieldException {
        Field field = carClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    @Test
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Car> clazz = Car.class;
        Constructor<Car> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Car car = constructor.newInstance("소나타", 10_000);

        assertThat(car).isNotNull();
    }
}
