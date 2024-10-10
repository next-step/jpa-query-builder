package study;

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
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.info("[클래스 정보 출력]");
        logger.debug("name=" + carClass.getName());
        logger.debug("modifiers=" + carClass.getModifiers());

        // 모든 필드 정보 출력
        logger.info("[모든 필드 정보 출력]");
        logger.info("[필드] 접근 가능한 public 필드 목록");
        for (Field field : carClass.getFields()) {
            logger.debug("name=" + field.getName() + " modifiers=" + field.getModifiers());
        }
        logger.info("[필드] 모든 필드 목록");
        for (Field field : carClass.getDeclaredFields()) {
            logger.debug("name=" + field.getName() + " modifiers=" + field.getModifiers());
        }

        // 생성자 정보 출력
        logger.info("[생성자] 접근 가능한 public 생성자 목록");
        for (Constructor<?> constructor : carClass.getConstructors()) {
            logger.debug(constructor.getName() + ": modifiers=" + constructor.getModifiers() + " parameterTypes=" + Arrays.toString(
                constructor.getParameterTypes()));
        }
        logger.info("[생성자] 모든 생성자 목록");
        for (Constructor<?> constructor : carClass.getDeclaredConstructors()) {
            logger.debug(constructor.getName() + ": modifiers=" + constructor.getModifiers() + " parameterTypes=" + Arrays.toString(
                constructor.getParameterTypes()));
        }

        // 메서드 정보 출력
        logger.info("[메서드] 부모 클래스, 자신 클래스의 접근 가능한 public 메서드 목록");
        for (Method method : carClass.getMethods()) {
            logger.debug(method.getName() + ": modifiers=" + method.getModifiers() + " parameterTypes=" + Arrays.toString(
                method.getParameterTypes()));
        }

        logger.info("[메서드] 모든 메서드 목록");
        for (Method method : carClass.getDeclaredMethods()) {
            logger.debug(method.getName() + ": modifiers=" + method.getModifiers() + " parameterTypes=" + Arrays.toString(
                method.getParameterTypes()));
        }
    }

}
