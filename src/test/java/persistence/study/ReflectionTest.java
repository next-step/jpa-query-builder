package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;

        assertAll(
                () -> assertThat(carClass.getName()).isEqualTo("persistence.study.Car"),
                () -> assertThat(carClass.getSimpleName()).isEqualTo("Car"),
                () -> assertThat(carClass.getPackage().getName()).isEqualTo("persistence.study")
        );
    }
}
