package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private static final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {

        logger.debug(carClass.getName());

        // 모든 필드 출력
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));

        // 모든 생성자 출력
        logger.debug(Arrays.toString(carClass.getConstructors()));

        // 모든 메서드 출력
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));

    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() {

        // get Car instance first
        Car carFromDefaultConstructor;
        try {
            carFromDefaultConstructor = carClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        // Car 객체의 메소드 중 `test로 시작하는 메소드를 자동으로 실행`
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        logger.debug((String) method.invoke(carFromDefaultConstructor));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {

        // get Car instance first
        Car carFromDefaultConstructor;
        try {
            carFromDefaultConstructor = carClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        // `@PrintView` 애노테이션으로 설정되어 있는 메소드만 Java Reflection을 활용해 실행하도록 구현.
        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    try {
                        method.invoke(carFromDefaultConstructor);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });


    }
}
