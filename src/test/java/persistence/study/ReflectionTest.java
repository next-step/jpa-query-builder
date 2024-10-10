package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    @DisplayName("getSimpleName() 메서드는 클래스의 이름을 반환한다")
    @Test
    void getSimpleName() {
        Class<Car> carClass = Car.class;

        assertThat(carClass.getSimpleName()).isEqualTo("Car");
    }

    @DisplayName("getDeclaredFields() 메서드는 클래스에 선언된 필드를 반환한다")
    @Test
    void getDeclaredFields() {
        Class<Car> carClass = Car.class;
        List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
                .map(Field::getName)
                .toList();

        assertThat(fieldNames).containsExactly("name", "price");
    }
}
