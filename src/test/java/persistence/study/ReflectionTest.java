package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReflectionTest {
    @Test
    @DisplayName("Car 클래스가 존재하는지 테스트")
    void testCarClassExists() {
        final Class<Car> carClass = Car.class;
        assertNotNull(carClass);
    }
}
