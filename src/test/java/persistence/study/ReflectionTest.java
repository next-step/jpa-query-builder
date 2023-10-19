package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private final Class<Car> carClass = Car.class;
    private static final String name = "테슬라";
    private static final int price = 99999;

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        logger.debug(carClass.getName());

        for (Field field : carClass.getDeclaredFields()) {
            logger.debug(field.getName());
        }

        for (Constructor<?> constructor : carClass.getDeclaredConstructors()) {
            logger.debug(constructor.getName());
        }

        for (Method method : carClass.getDeclaredMethods()) {
            logger.debug(method.getName());
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        final Car car = carClass.getConstructor().newInstance();

        Stream.of(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    method.setAccessible(true);
                    String result = invokeTestMethod(method, car);
                    assertThat(result).startsWith("test");
                });
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        final Car car = carClass.getConstructor().newInstance();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Stream.of(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    method.setAccessible(true);
                    invokeTestMethod(method, car);
                });

        assertThat(outputStream.toString()).contains("자동차 정보를 출력 합니다.");
    }

    @ParameterizedTest
    @MethodSource("provideFields")
    @DisplayName("private field에 값 할당")
    void privateFieldAccess(String fieldName, Object expected) throws Exception {
        final Car car = carClass.getConstructor().newInstance();

        Field field = carClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(car, getValue(field));
        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method getterMethod = car.getClass().getMethod(getterMethodName);
        Object result = getterMethod.invoke(car);

        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideFields() {
        return Stream.of(
                Arguments.of("name", name),
                Arguments.of("price", price)
        );
    }

    private Object getValue(Field field) {
        return field.getType() == int.class ? price : name;
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Car car = carClass.getConstructor(String.class, int.class).newInstance(name, price);

        assertAll(
                () -> assertThat(car.getName()).isEqualTo(name),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }

    private String invokeTestMethod(Method method, Car car) {
        try {
            return (String) method.invoke(car);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
