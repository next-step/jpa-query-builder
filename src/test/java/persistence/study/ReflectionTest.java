package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @DisplayName("Car 객체 정보 가져오기")
    @Test
    void showClass() {
        final Class<Car> carClass = Car.class;

        Arrays.stream(carClass.getDeclaredFields()).forEach(System.out::println);
        Arrays.stream(carClass.getConstructors()).forEach(System.out::println);
        Arrays.stream(carClass.getDeclaredMethods()).forEach(System.out::println);
    }
}
