package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        String builder = getClassName(carClass) +
                getFields(carClass) +
                getConstructors(carClass) +
                getMethods(carClass);
        logger.debug(builder);
    }

    private String getClassName(Class<Car> clazz) {
        return "클래스명: " + clazz.getName() + System.lineSeparator();
    }

    private String getFields(Class<Car> clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append("필드: ").append(System.lineSeparator());
        for (Field field : clazz.getDeclaredFields()) {
            builder.append(field.getModifiers())
                    .append(" ")
                    .append(field.getName())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String getConstructors(Class<Car> clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append("생성자: ").append(System.lineSeparator());
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            builder.append(constructor.getModifiers())
                    .append(" ")
                    .append(constructor.getName());
            builder.append("(")
                    .append(getParameterTypes(constructor))
                    .append(")")
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String getParameterTypes(Constructor<?> constructor) {
        Class<?>[] classes = constructor.getParameterTypes();
        String[] allTypes = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            allTypes[i] = classes[i].getName();
        }
        return String.join(", ", allTypes);
    }

    private String getMethods(Class<Car> clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append("메서드: ").append(System.lineSeparator());
        for (Method method : clazz.getDeclaredMethods()) {
            builder.append(method.getModifiers()).append(" ")
                    .append(method.getName())
                    .append(System.lineSeparator());
        }
        return builder.toString();
    }

    @Test
    void testMethodRun() {
        Car car = new Car("Dream Car", 100_000_000);
        StringBuilder builder = new StringBuilder();
        for (Method method : car.getClass().getMethods()) {
            invokeMethod(car, method)
                    .ifPresent((obj) -> builder.append(obj).append(System.lineSeparator()));
        }
        logger.debug(builder.toString());
    }

    private Optional<Object> invokeMethod(Car car, Method method) {
        if (method.getName().contains("test")) {
            return invoke(car, method);
        }
        return Optional.empty();
    }

    private Optional<Object> invoke(Car car, Method method) {
        try {
            Object returnObject = method.invoke(car);
            return Optional.of(returnObject);
        } catch (Exception exception) {
            logger.error("Method.invoke() 예외 발생", exception);
        }
        return Optional.empty();
    }
}

