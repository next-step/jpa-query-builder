package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체의 모든 필드 정보 출력하기")
    void showDeclaredFields() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        List<String> fieldNames = Arrays.stream(declaredFields)
                .map(Field::getName)
                .toList();
        assertThat(fieldNames).contains("name", "price");
    }

}
