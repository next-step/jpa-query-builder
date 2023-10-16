package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReflectTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectTest.class);
    private final Class<Car> carClass = Car.class;

    @Test
    @DisplayName("Car 객체 정보를 가져와 출력한다.")
    void showClass() {
        logger.debug(Arrays.toString(carClass.getDeclaredFields()));
        logger.debug(Arrays.toString(carClass.getDeclaredConstructors()));
        logger.debug(Arrays.toString(carClass.getDeclaredMethods()));
    }

    @Test
    @DisplayName("test로 시작하는 메소드를 실행한다.")
    void testMethodRun() throws Exception {
        List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        for (Method method : methods) {
            assertThat((String) method.invoke(carClass.newInstance())).startsWith("test : ");
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션이 표시된 메소드를 실행한다.")
    void testAnnotationMethodRun() throws Exception {
        List<Method> methods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        // @PrintView 애노테이션은 return type이 void기 때문에, null로 검증
        for (Method method : methods) {
            assertThat(method.invoke(carClass.newInstance())).isNull();
        }
    }

    @DisplayName("Private Field에 값을 할당한다.")
    @ParameterizedTest
    @MethodSource("privateFieldAccessParams")
    void privateFieldAccess(String fieldName, Object expected) throws Exception {
        Car car = new Car("S60", 5000);

        Field field = car.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(car, expected);

        assertThat(field.get(car)).isEqualTo(expected);
    }

    private static Stream<Arguments> privateFieldAccessParams() {
        return Stream.of(
                Arguments.of("name", "BMW 3 Series"),
                Arguments.of("price", 6000)
        );
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스를 생성한다.")
    void constructorWithArgs() throws Exception {
        Constructor<?> allArgsConstructor = Arrays.stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == carClass.getDeclaredFields().length)
                .findFirst()
                .get();
        Car car = (Car) allArgsConstructor.newInstance("S60", 5000);

        assertAll(() -> {
            assertThat(car.getName()).isEqualTo("S60");
            assertThat(car.getPrice()).isEqualTo(5000);
        });
    }

}
