package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    @Test
    @DisplayName("클래스 이름 검증")
    void testReflectionAPI() {
        Class<Car> carClass = Car.class;
        String carClassName = carClass.getSimpleName();

        assertThat(carClassName).isEqualTo("Car");
    }

    @Test
    @DisplayName("클래스 메서드 갯수 검증")
    void testReflectionModifiers() {
        Class<Car> carClass = Car.class;
        Integer length = carClass.getDeclaredMethods().length;

        assertThat(length).isEqualTo(2);
    }

    @Test
    @DisplayName("클래스 필드 검증")
    void testExtractClassInfo() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        Field declaredField = declaredFields[0];

        assertAll("infos", () -> {
            assertThat(declaredFields.length).isEqualTo(2);
                assertThat(declaredField.getName()).isEqualTo("name");
                assertThat(declaredField.getType()).isEqualTo(String.class);
            }
        );
    }
}