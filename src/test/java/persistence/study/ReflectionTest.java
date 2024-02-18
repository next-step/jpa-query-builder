package persistence.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static persistence.study.ReflectionUtil.*;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private final Class<Car> carClass = Car.class;
    private Object carInstance;

    @BeforeEach
    void stepUp() throws Exception {
        this.carInstance = createNewInstance(carClass);
    }

    @Nested
    @DisplayName("Car 클래스의 정보를 가져온다.")
    class carInfo {
        @Test
        @DisplayName("Car 클래스의 필드이름을 가져온다.")
        void showClass() {
            List<String> result = Arrays.stream(carClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());

            assertThat(result).containsExactlyInAnyOrder("name", "price");
        }

        @Test
        @DisplayName("Car class 생성자 정보를 반환한다.")
        void showConstructor() {
            List<Integer> result = Arrays.stream(carClass.getDeclaredConstructors())
                    .map(Constructor::getParameterCount)
                    .collect(Collectors.toList());

            assertThat(result).containsExactlyInAnyOrder(0, 2);
        }

        @Test
        @DisplayName("Car class 메소드 정보를 반환한다.")
        void showMethod() {
            List<String> result = Arrays.stream(carClass.getDeclaredMethods())
                    .map(Method::getName)
                    .collect(Collectors.toList());

            assertThat(result).containsExactlyInAnyOrder("printView", "testGetPrice", "testGetName");
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드를 실행한다.")
    void runMethodStartWithTest() throws Exception {
        List<Object> result = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> runMethod(carInstance, method))
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("test : 123", "test : nextstep");
    }

    @Test
    @DisplayName("PrintView 어노테이션이 있는 메소드를 실행한다.")
    void runPrintView() {
        final OutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> runMethod(carInstance, method));

        assertThat(out.toString()).isEqualTo("자동차 정보를 출력 합니다.\n");
    }

    @Test
    @DisplayName("Car 클래스에 필드에 값을 할당한다.")
    void seField() {
        Arrays.stream(carInstance.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    final Object value = field.getType() == String.class ? "123" : 123;
                    setField(field, carInstance, value);
                });

        List<Object> result = Arrays.stream(carInstance.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> runMethod(carInstance, method))
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("test : 123", "test : 123");
    }

    @Test
    @DisplayName("Car 클래스에서 인자를 가진 인스턴스를 생성한다.")
    void createConstructWithParameter() throws Exception {
        List<Object> result = Arrays.stream(carInstance.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> runMethod(carInstance, method))
                .collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("test : 123", "test : nextstep");
    }

}
