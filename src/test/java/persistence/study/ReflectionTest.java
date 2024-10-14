package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("클래스의 모든 필드 출력")
    void showClassFields() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();

        Arrays.stream(declaredFields).forEach(field -> logger.info("field name: {}, type: {}", field.getName(), field.getType()));
    }

    @Test
    @DisplayName("클래스의 모든 생성자 출력")
    void showClassConstructors() {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getConstructors();

        Arrays.stream(constructors).forEach(constructor -> logger.info("constructor name: {}, parameterCount: {}", constructor.getName(), constructor.getParameterCount()));
    }

    @Test
    @DisplayName("클래스의 모든 메소드 출력")
    void showClassMethods() {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();

        Arrays.stream(methods).forEach(method -> logger.info("method name: {}, modifiers: {}, parameterType: {}", method.getName(), method.getModifiers(), method.getParameterTypes()));
    }


    @Test
    @DisplayName("클래스 이름 검증")
    void testReflectionAPI() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        assertThat(carClassName).isEqualTo("Car");
    }

    @Test
    @DisplayName("클래스 메서드 갯수 검증")
    void testReflectionModifiers() {
        Class<Car> carClass = Car.class;
        Integer length = carClass.getDeclaredMethods().length;

        assertThat(length).isEqualTo(2);
    }

    @Test
    @DisplayName("클래스 필드 검증")
    void testExtractClassInfo() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        Field declaredField = declaredFields[0];

        assertAll("infos", () -> {
            assertThat(declaredFields.length).isEqualTo(2);
                assertThat(declaredField.getName()).isEqualTo("name");
                assertThat(declaredField.getType()).isEqualTo(String.class);
            }
        );
    }
}