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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class ReflectionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 이름 가져오기")
    void showName() {
        Class<Car> carClass = Car.class;
        assertThat(carClass.getName()).isEqualTo("persistence.study.Car");
    }

    @Test
    @DisplayName("Car 객체 필드 정보 가져오기")
    void showFields() {
        Class<Car> carClass = Car.class;
        Field[] declaredFields = carClass.getDeclaredFields();
        assertThat(Arrays.stream(declaredFields).count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Car 생성자들을 가져오기")
    void showConstructors() {
        Class<Car> carClass = Car.class;
        Constructor<?>[] constructors = carClass.getConstructors();
        assertThat(Arrays.stream(constructors).count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Car 메서드들을 가져오기")
    void showMethods() {
        Class<Car> carClass = Car.class;
        List<String> actual = Arrays.stream(carClass.getMethods())
                .map(Method::getName)
                .collect(Collectors.toList());
        assertThat(actual).contains("testGetName", "printView");
    }

    @Test
    @DisplayName("test로 시작하는 메서드를 실행한다")
    void executeMethodsStartingWithTest() throws Exception {
        Class<Car> carClass = Car.class;
        Method[] declaredMethods = carClass.getDeclaredMethods();
        List<Method> test = Arrays.stream(declaredMethods)
                .filter(it -> it.getName().startsWith("test"))
                .collect(Collectors.toList());
        Car car = carClass.getDeclaredConstructor().newInstance();

        for (Method method : test) {
            LOGGER.debug(String.valueOf(method));
            Object invoke = method.invoke(car);
        }
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메서드 실행한다.")
    void executeMethodsWithPritViewAnnotation() throws Exception {
        Class<Car> carClass = Car.class;

        List<Method> printViewAnnotatedMethods = Arrays.stream(carClass.getDeclaredMethods())
                .filter(it -> it.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());
        Car car = carClass.getDeclaredConstructor().newInstance();

        for (Method printViewAnnotatedMethod : printViewAnnotatedMethods) {
            LOGGER.debug(String.valueOf(printViewAnnotatedMethod));
            printViewAnnotatedMethod.invoke(car);
        }
    }

    @Test
    @DisplayName("private field에 값을 할당할 수 있다.")
    void insertValueInPrivateField() throws Exception {
        Class<Car> carClass = Car.class;
        Car car = carClass.getDeclaredConstructor().newInstance();

        Field[] declaredFields = carClass.getDeclaredFields();
        List<Field> privateFields = Arrays.stream(declaredFields)
                .filter(it -> Modifier.isPrivate(it.getModifiers()))
                .collect(Collectors.toList());
        for (Field privateField : privateFields) {
            privateField.setAccessible(true);
            Class<?> type = privateField.getType();
            privateField.set(car, generateTestValue(type));
        }
        int expectedPrice = 1000;
        String expectedName = "carName";
        assertAll(
                () -> assertThat(car.getPrice()).isEqualTo(expectedPrice),
                () -> assertThat(car.getName()).isEqualTo(expectedName)
        );
    }

    @ParameterizedTest
    @MethodSource("provideConstructorWithArgs")
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs(String carName, Integer price) throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<Car> declaredConstructor = carClass.getDeclaredConstructor(String.class, int.class);
        Car car = declaredConstructor.newInstance(carName, price);
        assertAll(
                () -> assertThat(car.getName()).isEqualTo(carName),
                () -> assertThat(car.getPrice()).isEqualTo(price)
        );
    }

    private static Stream<Arguments> provideConstructorWithArgs() {
        return Stream.of(
                Arguments.of("carName", 1000)
        );
    }

    private Object generateTestValue(Class<?> targetClass) {
        if (targetClass.equals(String.class)) {
            return "carName";
        }
        if (targetClass.equals(int.class) || targetClass.equals(Integer.class)) {
            return 1000;
        }
        throw new UnsupportedOperationException();
    }
}
