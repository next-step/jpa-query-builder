package persistence.reflectiontest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.")
    void showClass() {
        // given
        String expectedClassName = "persistence.reflectiontest.Car";
        List<String> expectedFieldNames = List.of("name", "price");
        List<String> expectedConstructorNames = List.of("persistence.reflectiontest.Car", "persistence.reflectiontest.Car");
        List<String> expectedMethodNames = List.of("printView", "testGetName", "testGetPrice");

        // when
        Class<Car> carClass = Car.class;
        String className = carClass.getName();

        List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());


        List<String> constructorNames = Arrays.stream(carClass.getDeclaredConstructors())
                .map(Constructor::getName)
                .collect(Collectors.toList());


        List<String> methodNames = Arrays.stream(carClass.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.toList());

        // then
        logger.debug(className);
        fieldNames.forEach(logger::debug);
        constructorNames.forEach(logger::debug);
        methodNames.forEach(logger::debug);
        assertAll(
                () -> assertThat(className).isEqualTo(expectedClassName),
                () -> assertThat(fieldNames).containsExactlyInAnyOrderElementsOf(expectedFieldNames),
                () -> assertThat(constructorNames).containsExactlyInAnyOrderElementsOf(expectedConstructorNames),
                () -> assertThat(methodNames).containsExactlyInAnyOrderElementsOf(expectedMethodNames)
        );
    }

    @Test
    @DisplayName("Car 객체의 메소드 중 test로 시작하는 메소드를 실행한다.")
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        List<String> expectedResults = List.of("test : null", "test : 0");
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        // when
        List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        List<String> actualResults = new ArrayList<>();
        for (Method method : methods) {
            actualResults.add((String) method.invoke(car));
        }

        // then
        assertThat(actualResults).containsExactlyInAnyOrderElementsOf(expectedResults);
    }

}
