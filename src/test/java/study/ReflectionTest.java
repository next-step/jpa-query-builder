package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        Arrays.stream(carClass.getDeclaredFields())
                .forEach(field -> logger.debug("필드 이름 : " + field.getName()));
        Arrays.stream(carClass.getDeclaredConstructors())
                .forEach(constructor -> logger.debug("생성자 이름 : " + constructor.getName()));
        Arrays.stream(carClass.getMethods())
                .forEach(method -> logger.debug("메소드 이름 : " + method.getName()));
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() {
        Car testCar = new Car("테스트카", 10000);
        Class<? extends Car> carClass = testCar.getClass();
        Arrays.stream(carClass.getDeclaredMethods()).forEach(
                method -> {
                    if (method.getName().startsWith("test")) {
                        try {
                            Object invoke = method.invoke(testCar);
                            logger.debug("메소드 리턴 값 : " + invoke);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException("접근할 수 없는 필드");
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException("메소드 응답 예외 발생");
                        }
                    }
                }
        );
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        Car testCar = new Car("테스트카", 10000);
        Class<? extends Car> testCarClass = testCar.getClass();
        Arrays.stream(testCarClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(testCar);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("접근할 수 없는 필드");
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException("메소드 응답 예외 발생");
                    }
                });
    }
}
