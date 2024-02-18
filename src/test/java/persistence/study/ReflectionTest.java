package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @DisplayName("Car 객체 필드명 가져오기")
    @Test
    void showClassFields() {
        // Given
        final Class<Car> carClass = Car.class;

        // When
        final List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());

        // Then
        assertThat(fieldNames).contains("name", "price");
    }

    @DisplayName("Car 객체 생성자 가져오기")
    @Test
    void showClassConstructs() {
        // Given
        final Class<Car> carClass = Car.class;

        // When
        final Long constructSize = Arrays.stream(carClass.getConstructors())
                .map(Constructor::getName)
                .count();

        // Then
        assertThat(constructSize).isEqualTo(2);
    }

    @DisplayName("Car 객체 메서드 가져오기")
    @Test
    void showClassMethods() {
        // Given
        final Class<Car> carClass = Car.class;

        // When
        final List<String> methodNames = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toList());

        // Then
        assertThat(methodNames).contains("printView", "testGetName", "testGetPrice", "getName", "getPrice");
    }

    @DisplayName("test로 시작하는 메소드 실행한다.")
    @Test
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Given
        final String name = "simpson";
        final int price = 1500;
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getDeclaredConstructor(String.class, int.class).newInstance(name, price);

        // When
        final List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("test"))
                .collect(Collectors.toList());
        List<String> actualResults = new ArrayList<>();
        for (Method method : methods) {
            actualResults.add((String) method.invoke(car));
        }

        // Then
        String expectedTestGetNameResult = String.format("test : %s", name);
        String expectedTestGetPriceResult = String.format("test : %d", price);
        assertThat(actualResults).contains(expectedTestGetNameResult, expectedTestGetPriceResult);
    }

    @DisplayName("@PrintView 애노테이션 메소드 실행")
    @Test
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Given
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        // When
        final List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        // Then
        for (Method method : methods) {
            method.invoke(car);
        }
    }

    @DisplayName("private field에 값 할당")
    @Test
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Given
        final String name = "simpson";
        final int price = 100;
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor().newInstance();

        // When
        final List<Field> fields = Arrays.stream(carClass.getDeclaredFields())
                .filter(f -> f.getModifiers() == Modifier.PRIVATE)
                .collect(Collectors.toList());

        for (Field privateField : fields) {
            privateField.setAccessible(true);
            changeCarName(name, car, privateField);
            changeCarPrice(price, car, privateField);
        }

        // Then
        assertThat(car.getName()).isEqualTo(name);
        assertThat(car.getPrice()).isEqualTo(price);
    }

    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    @Test
    void constructorWithArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Given
        final String name = "simpson";
        final int price = 100;
        final Class<Car> carClass = Car.class;

        // When
        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        // Then
        assertThat(car).isEqualTo(new Car(name, 100));
    }

    private void changeCarPrice(final int price, final Car car, final Field privateField) throws IllegalAccessException {
        if (privateField.getName().equals("price")) {
            privateField.set(car, price);
        }
    }

    private void changeCarName(final String name, final Car car, final Field privateField) throws IllegalAccessException {
        if (privateField.getName().equals("name")) {
            privateField.set(car, name);
        }
    }
}
