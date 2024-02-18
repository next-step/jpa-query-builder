package persistence.reflectiontest;

import org.junit.jupiter.api.DisplayName;
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

class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
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
}
