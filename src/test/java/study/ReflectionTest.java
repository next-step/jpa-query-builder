package study;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug("Class Name: " + carClass.getName());

        logger.debug("--- Fields ---");
        Field[] fields = carClass.getDeclaredFields();
        for (Field field : fields) {
            logger.debug(
                "Field: " + field.getName() +
                ", Type: " + field.getType().getName() +
                ", Modifiers: " + field.getModifiers()
            );
        }

        logger.debug("--- Constructors ---");
        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            logger.debug(
                "Constructor: " + constructor.getName() +
                ", Parameter Count: " + constructor.getParameterCount() +
                ", Modifiers: " + constructor.getModifiers()
            );
        }

        logger.debug("--- Methods ---");
        Method[] methods = carClass.getDeclaredMethods();
        for (Method method : methods) {
            logger.debug(
                "Method: " + method.getName() +
                ", Parameter Count: " + method.getParameterCount() +
                ", Return Type: " + method.getReturnType().getName() +
                ", Modifiers: " + method.getModifiers()
            );
        }
    }

}
