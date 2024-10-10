package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug("==================================================");
        logger.debug("class name: {}", carClass.getName());
        logger.debug("==================================================");

        for (int i = 0; i < carClass.getDeclaredFields().length; i++) {
            logger.debug("field[{}] name: {}", i, carClass.getDeclaredFields()[i].getName());
            logger.debug("field[{}] modifiers: {}", i, carClass.getDeclaredFields()[i].getModifiers());
            logger.debug("field[{}] type: {}", i, carClass.getDeclaredFields()[i].getType());
            logger.debug("==================================================");
        }

        for (int i = 0; i < carClass.getConstructors().length; i++) {
            logger.debug("constructor[{}] name: {}", i, carClass.getConstructors()[i].getName());
            logger.debug("constructor[{}] modifiers: {}", i, carClass.getConstructors()[i].getModifiers());
            logger.debug("constructor[{}] parameter types: {}", i, carClass.getConstructors()[i].getParameterTypes());
            logger.debug("==================================================");
        }

        for (int i = 0; i < carClass.getDeclaredMethods().length; i++) {
            logger.debug("method[{}] name: {}", i, carClass.getDeclaredMethods()[i].getName());
            logger.debug("method[{}] modifiers: {}", i, carClass.getDeclaredMethods()[i].getModifiers());
            logger.debug("method[{}] parameter types: {}", i, carClass.getDeclaredMethods()[i].getParameterTypes());
            logger.debug("method[{}] return type: {}", i, carClass.getDeclaredMethods()[i].getReturnType());
            logger.debug("method[{}] annotations: {}", i, carClass.getDeclaredMethods()[i].getAnnotations());
            logger.debug("==================================================");
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<Car> constructor = carClass.getConstructor();
        Car car = constructor.newInstance();

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        Object result = method.invoke(car);
                        if (Objects.nonNull(result)) {
                            logger.debug((String) result);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
