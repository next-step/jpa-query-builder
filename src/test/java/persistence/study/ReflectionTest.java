package persistence.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private static final Class<Car> CAR_CLASS = Car.class;
    private static final String CAR_NAME = "렉서스";
    private static final int CAR_PRICE = 999;

    private Car car;

    @BeforeEach
    void setup() {
        try {
            // Note : Integer.class NG, int.class OK
            car = CAR_CLASS.getDeclaredConstructor(String.class, int.class).newInstance(CAR_NAME, CAR_PRICE);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeCarMethod(Method method) {
        try {
            return method.invoke(car);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Reflection을 이용해 Car 객체 정보를 가져올수 있어야 한다")
    void showClass() {
        assertAll(
                () -> assertThat(CAR_CLASS.getSimpleName()).isEqualTo("Car"),
                () -> assertThat(Arrays.stream(CAR_CLASS.getDeclaredFields())
                        .map(Field::getName)
                        .collect(Collectors.toUnmodifiableList()))
                        .containsAll(List.of("name", "price")),
                () -> assertThat(CAR_CLASS.getConstructors()).hasSize(2),
                () -> assertThat(Arrays.stream(CAR_CLASS.getDeclaredMethods())
                        .map(Method::getName)
                        .collect(Collectors.toUnmodifiableList()))
                        .containsAll(List.of("printView", "testGetName", "testGetPrice"))
        );
    }

    @Test
    @DisplayName("Reflection을 이용해 test로 시작하는 메소드 실행할 수 있어야 한다")
    void testMethodRun() {
        final List<String> testMethodsInvokeResults = Arrays.stream(CAR_CLASS.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .map(method -> (String) invokeCarMethod(method))
                .collect(Collectors.toUnmodifiableList());

        assertThat(testMethodsInvokeResults).containsAll(List.of("test : " + CAR_NAME, "test : " + CAR_PRICE));
    }


    @Test
    @DisplayName("Reflection을 이용해 @PrintView 애노테이션 메소드를 실행할 수 있어야 한다")
    void testAnnotationMethodRun() {
        // @PrintView 메서드 output 캡처를 위해 일시적으로 System.out을 변경
        PrintStream originOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Arrays.stream(CAR_CLASS.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(this::invokeCarMethod);
        // restore origin System.out
        System.setOut(originOut);

        assertThat(outContent.toString().trim()).isEqualTo("자동차 정보를 출력 합니다.");
    }

    @Test
    @DisplayName("Reflection을 이용해 private field에 값 할당할 수 있어야 한다")
    void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {

        // given
        Field nameField = CAR_CLASS.getDeclaredField("name");
        Field priceField = CAR_CLASS.getDeclaredField("price");
        nameField.setAccessible(true);
        priceField.setAccessible(true);

        // when
        nameField.set(car, "테슬라");
        priceField.set(car, 1);

        // then
        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : 테슬라"),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : 1")
        );
    }

    @Test
    @DisplayName("Reflection을 이용해 인자를 가진 생성자의 인스턴스 생성이 가능해야 한다")
    void constructorWithArgs() {
        assertAll(
                () -> assertThat(car.testGetName()).isEqualTo("test : " + CAR_NAME),
                () -> assertThat(car.testGetPrice()).isEqualTo("test : " + CAR_PRICE)
        );
    }
}
