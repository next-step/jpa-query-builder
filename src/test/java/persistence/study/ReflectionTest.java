package persistence.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ReflectionTest {
    private Class<Car> testClass;
    private static final String CAR_NAME = "K9";
    private static final int CAR_PRICE = 10000000;

    @BeforeEach
    void setUp() {
        testClass = Car.class;
    }

    @Test()
    @DisplayName("Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.")
    void showClass() {
        List<String> fieldNames = Arrays.stream(testClass.getDeclaredFields())
                .map(Field::getName)
                .toList();

        List<String> methodNames = Arrays.stream(testClass.getDeclaredMethods())
                .map(Method::getName)
                .toList();

        List<Integer> constructorsArgsCount = Arrays.stream(testClass.getConstructors())
                .map(Constructor::getParameterCount)
                .toList();

        assertAll(
                () -> assertThat(fieldNames).containsExactlyInAnyOrderElementsOf(List.of("name", "price")),
                () -> assertThat(methodNames).containsExactlyInAnyOrderElementsOf(
                        List.of("testGetName", "testGetPrice", "printView", "getName", "getPrice")),
                () -> assertThat(constructorsArgsCount).containsExactlyInAnyOrderElementsOf(List.of(0, 2))
        );
    }

    @Test
    @DisplayName("\"test\"로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Car car = testClass.getDeclaredConstructor(String.class, int.class).newInstance(CAR_NAME, CAR_PRICE);

        List<Method> testMethods = Arrays.stream(testClass.getMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();

        for (Method method : testMethods) {
            String expectedResult = switch (method.getName()) {
                case "testGetName" -> "test : " + CAR_NAME;
                case "testGetPrice" -> "test : " + CAR_PRICE;
                default -> throw new NoSuchMethodException();
            };
            assertEquals(method.invoke(car), expectedResult);
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Car car = testClass.getDeclaredConstructor().newInstance();

        Arrays.stream(testClass.getMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        assertNull(method.invoke(car));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws Exception {
        Car car = testClass.getDeclaredConstructor().newInstance();

        Map<String, Object> propertyMap = Map.of("name", CAR_NAME, "price", CAR_PRICE);
        propertyMap.forEach((key, value) -> {
            try {
                Field field = testClass.getDeclaredField(key);
                field.setAccessible(true);
                field.set(car, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        assertAll(
                () -> assertEquals(car.getName(), CAR_NAME),
                () -> assertEquals(car.getPrice(), CAR_PRICE)
        );
    }

    @Test()
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void getDeclaredConstructors() throws Exception {
        Car car = testClass.getDeclaredConstructor(String.class, int.class).newInstance(CAR_NAME, CAR_PRICE);

        assertAll(
                () -> assertEquals(car.getName(), CAR_NAME),
                () -> assertEquals(car.getPrice(), CAR_PRICE)
        );
    }
}
