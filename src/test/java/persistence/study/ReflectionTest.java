package persistence.study;



import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);


    @Test
    @DisplayName("요구사항 1 - Car 클래스 정보(모든 필드, 생성자, 메소드) 출력")
    void showClass() {
        Class<Car> carClass = Car.class;

        logger.debug("Car 클래스의 모든 필드 {}",  Arrays.toString(carClass.getDeclaredFields()));
        logger.debug("Car 클래스의 생성자 {}",  Arrays.toString(carClass.getDeclaredFields()));
        logger.debug("Car 클래스의 메소드 {}",  Arrays.toString(carClass.getDeclaredFields()));
    }

    @Test
    @DisplayName("요구사항 2 - test로 시작하는 메소드 실행")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        Car car = carClass.getDeclaredConstructor().newInstance();

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                logger.debug("메소드 호출 {}", method.invoke(car));
            }
        }
    }

}
