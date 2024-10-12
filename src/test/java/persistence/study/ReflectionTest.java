package persistence.study;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 모든 필드 정보 가지고 오기 ")
    void showClass() {
        Class<Car> carClass = Car.class;
        Field[] fields = carClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            logger.info("name: " + field.getName() + ", type: " + field.getType());
        });
    }
    @Test
    @DisplayName("Car 객체 생성자 정보 가지고 오기 ")
    void showClass_2() {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        Arrays.stream(constructors).forEach(constructor -> {
            logger.info("Constructor: " + constructor.getName() + ", Parameter Types " + Arrays.toString(constructor.getParameterTypes()));
        });
    }
    @Test
    @DisplayName("Car 객체 메서드에 대한 정보 가지고 오기 ")
    void showClass_3() {
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            logger.info("Method: " + method.getName() + ", Return Type: " + method.getReturnType());
        });
    }
}