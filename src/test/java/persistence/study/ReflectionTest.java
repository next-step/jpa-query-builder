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

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());

        // Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
        for (Method method : carClass.getDeclaredMethods()) {
            logger.debug(method.getName());
        }
        for (Field field : carClass.getFields()) {
            logger.debug(field.getName());
        }
        for (Constructor<?> constructor : carClass.getConstructors()) {
            logger.debug(constructor.getName());
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    public void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = Car.class.getConstructor(String.class, int.class).newInstance("Porsche", 10000);
        for (Method method : carClass.getDeclaredMethods()) {
            if (isTestMethod(method)) {
                methodRun(car, method);
            }
        }
    }

    private void methodRun(Object instance, Method method) throws Exception {
        method.setAccessible(true);
        logger.debug((String) method.invoke(instance));
    }

    private boolean isTestMethod(Method method) {
        return method.getName().startsWith("test");
    }

}
