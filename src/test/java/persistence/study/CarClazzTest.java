package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CarClazzTest {
    private static final Logger logger = LoggerFactory.getLogger(CarClazzTest.class);

    @DisplayName("print Car class' fields, constructors, methods")
    @Test
    void printCarClazz() {
        Class<Car> clazz = Car.class;

        String fieldNames = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.joining(","));
        String constructorNames = Arrays.stream(clazz.getDeclaredConstructors()).map(Constructor::getName).collect(Collectors.joining(","));
        String methodNames = Arrays.stream(clazz.getDeclaredMethods()).map(Method::getName).collect(Collectors.joining(","));
        logger.info(fieldNames);
        logger.info(constructorNames);
        logger.info(methodNames);
    }
}
