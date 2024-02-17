package persistence.reflectiontest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        // Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력
        Class<Car> carClass = Car.class;

        // fields
        Arrays.stream(carClass.getDeclaredFields())
                .forEach(field -> logger.debug(field.getName()));

        // constructors
        Arrays.stream(carClass.getConstructors())
                .forEach(constructor -> logger.debug(constructor.getName()));

        // methods
        Arrays.stream(carClass.getMethods())
                .forEach(method -> logger.debug(method.getName()));
    }



}
