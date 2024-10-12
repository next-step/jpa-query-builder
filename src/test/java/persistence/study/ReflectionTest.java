package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.logging.Logger;

public class ReflectionTest {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
    }

    

}
