package step1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {
    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car("포르쉐", 1000000);
    }

    @Test
    @DisplayName("요구사항 1 - 클래스 정보 출력")
    void testClassInfo() {
        Class<Car> carClass = Car.class;
        String simpleName = carClass.getSimpleName();
        Field[] declaredFields = carClass.getDeclaredFields();
        Constructor<?>[] declaredConstructors = carClass.getDeclaredConstructors();
        Method[] methods = carClass.getDeclaredMethods();

        assertAll(
                "Simple Name Test",
                () -> assertThat(simpleName)
                        .isEqualTo("Car")
        );

        assertAll(
                "Field Test",
                () -> assertThat(declaredFields[0].getName())
                        .isEqualTo("name"),
                () -> assertThat(declaredFields[0].getType())
                        .isEqualTo(String.class),
                () -> assertThat(declaredFields[1].getName())
                        .isEqualTo("price"),
                () -> assertThat(declaredFields[1].getType())
                        .isEqualTo(int.class)
        );

        assertAll(
                "Constructor Test",
                () -> assertThat(declaredConstructors.length)
                        .isEqualTo(2)
        );

        assertAll(
                "Method Test",
                () -> assertThat(methods.length)
                        .isEqualTo(3)
        );
    }

    @Test
    @DisplayName("요구사항 2 - test로 시작하는 메소드 실행")
    void testPrefixMethod() throws InvocationTargetException, IllegalAccessException {
        final List<Object> actual = new ArrayList<>();
        for (Method declaredMethod : Car.class.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("test")) {
                actual.add(declaredMethod.invoke(car));
            }
        }
        assertThat(actual).containsExactlyInAnyOrder(
                "test : 포르쉐",
                "test : 1000000"
        );
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() throws InvocationTargetException, IllegalAccessException {
        Arrays.stream(Car.class.getDeclaredMethods())
                .filter(
                        method -> method.isAnnotationPresent(PrintView.class)
                ).findAny()
                .get().invoke(car);
    }

    @Test
    @DisplayName("요구사항 4 - private field 에 값 할당")
    void testPrivateAccess() throws IllegalAccessException, NoSuchFieldException {
        Field name = Car.class.getDeclaredField("name");
        name.setAccessible(true);
        name.set(car, "소나타");
        assertThat(car.testGetName())
                .isEqualTo("test : 소나타");
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void testConstructor() throws
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException,
            NoSuchMethodException {
        Car car = Car.class
                .getConstructor(String.class, int.class)
                .newInstance(
                        "포르쉐",
                        1000000
                );
        assertAll(
                () -> assertThat(car.testGetName())
                        .isEqualTo("test : 포르쉐"),
                () -> assertThat(car.testGetPrice())
                        .isEqualTo("test : 1000000")
        );
    }
}
