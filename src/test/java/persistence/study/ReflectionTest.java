package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    @DisplayName("Car 클래스의 모든 메서드 정보를 출력한다")
    void printAllMethods() {
        final Class<Car> carClass = Car.class;
        final Method[] declaredMethods = carClass.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            final String modifiers = Modifier.toString(method.getModifiers());
            final String returnType = method.getReturnType().getSimpleName();
            final String name = method.getName();
            final String parameters = getParameters(method.getParameters());
            logger.info("Method: {} {} {} ({})", modifiers, returnType, name, parameters);
        }
    }

    @Test
    @DisplayName("Car 클래스의 test로 시작하는 메서드를 실행한다")
    void testMethodRun() throws Exception {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getDeclaredConstructor().newInstance();
        final Method[] methods = carClass.getDeclaredMethods();
        final List<String> results = getResults(methods, car);
        assertThat(results).containsExactly("test : null", "test : 0");
    }

    @Test
    @DisplayName("Car 클래스의 @PrintView 어노테이션이 붙은 메서드를 실행한다")
    void testAnnotationMethodRun() throws Exception {
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getDeclaredConstructor().newInstance();
        final Method[] methods = carClass.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(car);
            }
        }
    }

    private String getParameters(final Parameter[] parameters) {
        return Arrays.stream(parameters)
                .map(param -> param.getType().getSimpleName())
                .collect(Collectors.joining(", "));
    }

    private List<String> getResults(final Method[] methods, final Car car) throws Exception {
        final List<String> results = new ArrayList<>();
        for (final Method method : methods) {
            if (method.getName().startsWith("test")) {
                final Object invoke = method.invoke(car);
                results.add(invoke.toString());
            }
        }
        return results;
    }
}
