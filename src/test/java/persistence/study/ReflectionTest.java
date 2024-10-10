package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    @DisplayName("getSimpleName() 메서드는 클래스의 이름을 반환한다")
    @Test
    void getSimpleName() {
        Class<Car> carClass = Car.class;

        assertThat(carClass.getSimpleName()).isEqualTo("Car");
    }
}
