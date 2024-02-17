package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflectiontest.Car;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        // Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.

        for (Method method : carClass.getMethods()) {
            logger.debug(method.getName());
        }
        for (Field field : carClass.getFields()) {
            logger.debug(field.getName());
        }
        for (Constructor<?> constructor : carClass.getConstructors()) {
            logger.debug(constructor.getName());
        }
    }
}
