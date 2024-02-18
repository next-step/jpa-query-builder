package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReflectionTest {

    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    private final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("클래스 정보 출력")
    void showClass() {
        log.debug("carClass.getName() : {}", carClass.getName());

        assertDoesNotThrow(() -> logClass(carClass));
    }

    private void logClass(Class<?> clazz) {
        logFields(clazz);
        logMethods(clazz);
        logConstructors(clazz);
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        List<Method> testMethods = Arrays.stream(carClass.getDeclaredMethods())
            .filter(method -> method.getName().startsWith("test"))
            .collect(Collectors.toList());

        for (Method testMethod : testMethods) {
            log.debug("testMethod.getName() : {}", testMethod.getName());

            Car car = newInstanceByDefaultConstructor(carClass);
            Object returnValue = testMethod.invoke(car);

            assertAll(
                () -> assertThat(returnValue).isNotNull(),
                () -> assertThat(returnValue).isInstanceOf(String.class),
                () -> assertThat((String) returnValue).startsWith("test : ")
            );
        }

        assertThat(testMethods).hasSize(2);
    }

    @Test
    @DisplayName("@PrintView 어노테이션이 붙은 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        List<Method> printViewAnnotatedMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        Car car = newInstanceByDefaultConstructor(carClass);

        for (Method printViewAnnotatedMethod : printViewAnnotatedMethods) {
            log.debug("printViewAnnotatedMethod.getName() : {}", printViewAnnotatedMethod.getName());

            Object returnValue = printViewAnnotatedMethod.invoke(car);

            assertThat(returnValue).isNull();
        }
    }

    @ParameterizedTest
    @CsvSource(value = "name;테슬라;price;100000", delimiterString = ";")
    @DisplayName("private field에 값 할당")
    void privateFieldAccess(String field1, String value1, String field2, int value2) throws Exception {
        // Given
        Map<String, Object> fieldNameToValue = Map.of(
            field1, value1,
            field2, value2
        );

        Car car = newInstanceByDefaultConstructor(carClass);

        List<Field> privateFields = Arrays.stream(carClass.getDeclaredFields())
            .filter(field -> Modifier.isPrivate(field.getModifiers()))
            .collect(Collectors.toList());

        // When
        for (Field declaredField : privateFields) {
            logField(declaredField);

            String fieldName = declaredField.getName();

            if (fieldNameToValue.containsKey(fieldName)) {
                declaredField.setAccessible(true);
                declaredField.set(car, fieldNameToValue.get(fieldName));
            }
        }

        // Then
        assertAll(
            () -> assertThat(car.getName()).isEqualTo(value1),
            () -> assertThat(car.getPrice()).isEqualTo(value2)
        );
    }

    @ParameterizedTest
    @CsvSource(value = "테슬라;100000", delimiterString = ";")
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs(String name, int price) throws Exception {
        // Given
        Constructor<Car> requiredArgsConstructor = carClass.getDeclaredConstructor(
            String.class,
            int.class
        );

        logConstructor(requiredArgsConstructor);

        // When
        Car car = requiredArgsConstructor.newInstance(name, price);

        // Then
        assertAll(
            () -> assertThat(car).isNotNull(),
            () -> assertThat(car.getName()).isEqualTo(name),
            () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }

    private Car newInstanceByDefaultConstructor(Class<Car> carClass)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return carClass.getDeclaredConstructor().newInstance();
    }

    private void logFields(Class<?> clazz) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            logField(declaredField);
        }
    }

    private void logField(Field declaredField) {
        log.debug("declaredField.getName() : {}", declaredField.getName());
    }

    private void logMethods(Class<?> clazz) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            logMethod(declaredMethod);
        }
    }

    private void logMethod(Method declaredMethod) {
        log.debug("declaredMethod.getName() : {}", declaredMethod.getName());
        for (Class<?> parameterType : declaredMethod.getParameterTypes()) {
            log.debug("parameterType.getName() : {}", parameterType.getName());
        }

        for (Annotation annotation : declaredMethod.getAnnotations()) {
            log.debug("annotation.annotationType().getName() : {}", annotation.annotationType().getName());
        }

        log.debug("declaredMethod.getReturnType() : {}", declaredMethod.getReturnType());
    }

    private void logConstructors(Class<?> clazz) {
        for (Constructor<?> declaredConstructor : clazz.getDeclaredConstructors()) {
            logConstructor(declaredConstructor);
        }
    }

    private void logConstructor(Constructor<?> declaredConstructor) {
        log.debug("declaredConstructor.getName() : {}", declaredConstructor.getName());

        for (Class<?> parameterType : declaredConstructor.getParameterTypes()) {
            log.debug("parameterType.getName() : {}", parameterType.getName());
        }
    }
}
