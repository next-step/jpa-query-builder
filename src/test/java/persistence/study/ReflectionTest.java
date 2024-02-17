package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showFields() {
        Class<Car> carClass = Car.class;
        LOGGER.debug(carClass.getName());
        Field[] declaredFields = carClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            LOGGER.debug(String.valueOf(declaredField));
        }
    }

    @Test
    @DisplayName("Car 생성자들을 가져오기")
    void showContrcutors() {
        Class<Car> carClass = Car.class;
        LOGGER.debug(carClass.getName());
        Constructor<?>[] constructors = carClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            LOGGER.debug(String.valueOf(constructor));
        }
    }

    @Test
    @DisplayName("Car 메서드들을 가져오기")
    void showMethods() {
        Class<Car> carClass = Car.class;
        LOGGER.debug(carClass.getName());
        Method[] methods = carClass.getMethods();
        for (Method method : methods) {
            LOGGER.debug(String.valueOf(method));
        }
    }
}
