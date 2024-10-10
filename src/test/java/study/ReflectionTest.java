package study;

import org.assertj.core.api.Assertions;
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

    @Test
    @DisplayName("test로 시작하는 메서드 실행")
    void testMethodRun() throws Exception {
//        Class<? extends Car> carClass = Car.class;

        Car car = new Car("tesla", 199999999);
        Class<? extends Car> carClass = car.getClass();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                Object result = method.invoke(car);
                logger.debug("" + result);
            }
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<? extends Car> carClass = Car.class;

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrintView.class)) {
                method.invoke(carClass.newInstance());
            }
        }
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws Exception {
        // given
        String carName = "소나타";
        Car car = new Car();
        Class<? extends Car> carClass = car.getClass();
        Field field = carClass.getDeclaredField("name");

        // when
        field.setAccessible(true);
        field.set(car, carName);

        // then
        Assertions.assertThat(car.getName()).isEqualTo(carName);
    }

}
