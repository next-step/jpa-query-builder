package persistence.study;

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
        Class<Car> carClass = Car.class;
        // 패키지+클래스 이름
        logger.debug(carClass.getName());
        // 모든 필드 목록
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));
        // 모든 생성자 반환
        logger.debug(Arrays.toString(carClass.getDeclaredConstructors()));
        // 모든 메서드 반환
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));
    }
}
