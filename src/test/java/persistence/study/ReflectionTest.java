package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void startTestMethodsExecute() throws Exception {
        Car tesla = new Car("Tesla", 10_000);
        Class<? extends Car> carClass = tesla.getClass();

        List<Method> testStartMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();

        for (Method testStartMethod : testStartMethods) {
            Object invoke = testStartMethod.invoke(tesla);
            System.out.println(invoke);
        }
    }
}
