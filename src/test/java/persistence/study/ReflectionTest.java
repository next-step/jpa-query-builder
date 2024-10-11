package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    @Test()
    @DisplayName("Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.")
    void showClass() {
        Class<Car> testClass = Car.class;

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
                () -> assertThat(methodNames)
                        .containsExactlyInAnyOrderElementsOf(List.of("testGetName", "testGetPrice", "printView")),
                () -> assertThat(constructorsArgsCount).containsExactlyInAnyOrderElementsOf(List.of(0, 2))
        );
    }
}
