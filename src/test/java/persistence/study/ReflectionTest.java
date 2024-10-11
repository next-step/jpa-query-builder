package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 클래스가 존재하는지 테스트")
    void testCarClassExists() {
        final Class<Car> carClass = Car.class;
        assertNotNull(carClass);
    }

    @Test
    @DisplayName("Car 클래스의 모든 필드 정보를 출력한다")
    void printAllFields() {
        final Class<Car> carClass = Car.class;
        final Field[] declaredFields = carClass.getDeclaredFields();
        for (final Field field : declaredFields) {
            logger.info("Field: {} {} {}",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName());
        }
    }

    @Test
    @DisplayName("Car 클래스의 모든 생성자 정보를 출력한다")
    void printAllConstructors() {
        final Class<Car> carClass = Car.class;
        final Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        for (final Constructor<?> constructor : constructors) {
            final String modifiers = Modifier.toString(constructor.getModifiers());
            final String name = carClass.getSimpleName();
            final String parameters = getParameters(constructor.getParameters());
            logger.info("Constructor: {} {} ({})", modifiers, name, parameters);
        }
    }

    private String getParameters(final Parameter[] parameters) {
        return Arrays.stream(parameters)
                .map(param -> param.getType().getSimpleName())
                .collect(Collectors.joining(", "));
    }
}
