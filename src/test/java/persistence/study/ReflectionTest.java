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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private Class<Car> testClass;

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
                () -> assertThat(methodNames)
                        .containsExactlyInAnyOrderElementsOf(List.of("testGetName", "testGetPrice", "printView")),
                () -> assertThat(constructorsArgsCount).containsExactlyInAnyOrderElementsOf(List.of(0, 2))
        );
    }

    @Test
    @DisplayName("\"test\"로 시작하는 메소드 실행")
    void invokeClassMethod() throws Exception {
        Car car = testClass.getDeclaredConstructor(String.class, int.class).newInstance("그랜져", 1000000);

        List<Method> testMethods = Arrays.stream(testClass.getMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();

        for (Method method : testMethods) {
            String expectedResult = switch (method.getName()) {
                case "testGetName" -> "test : 그랜져";
                case "testGetPrice" -> "test : 1000000";
                default -> throw new NoSuchMethodException();
            };
            assertThat(method.invoke(car)).isEqualTo(expectedResult);
        }
    }
}
