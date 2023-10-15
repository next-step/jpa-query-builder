package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.debug(carClass.getName());
    }

    @Test
    @DisplayName("test 로 시작하는 메서드 실행")
    void testMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        Method[] declaredMethods = carClass.getDeclaredMethods();
        List<Method> testMethods = stream(declaredMethods)
                .filter(method -> method.getName().startsWith("test"))
                .collect(Collectors.toList());

        Car car = carClass.getDeclaredConstructor().newInstance();
        List<String> methodResults = testMethods.stream()
                .map(testMethod -> {
                    try {
                        return (String) testMethod.invoke(car);
                    } catch (Exception e) {
                        fail("Exception occurred invoking test method : {}", testMethod.getName());
                    }
                    return null;
                }).collect(Collectors.toList());

        assertAll(
                () -> assertThat(methodResults.size()).isEqualTo(2),
                () -> assertThat(methodResults).allSatisfy(methodResult -> assertThat(methodResult).startsWith("test"))
        );
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메서드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        Method[] declaredMethods = carClass.getDeclaredMethods();
        List<Method> annotationMethods = stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .collect(Collectors.toList());

        Car car = carClass.getDeclaredConstructor().newInstance();
        annotationMethods.forEach(annotationMethod -> {
            try {
                annotationMethod.invoke(car);
            } catch (Exception e) {
                fail("Exception occurred invoking test method : {}", annotationMethod.getName());
            }
        });

        assertThat(annotationMethods.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("private field에 값 할당")
    void privateFieldAccess() throws Exception {
        Class<Car> carClass = Car.class;
        Field nameField = carClass.getDeclaredField("name");
        nameField.setAccessible(true);
        Field priceField = carClass.getDeclaredField("price");
        priceField.setAccessible(true);

        Car car = carClass.getDeclaredConstructor().newInstance();
        nameField.set(car, "소나타");
        priceField.set(car, 10000);

        assertAll(
                () -> assertThat(car.testGetName()).contains("소나타"),
                () -> assertThat(car.testGetPrice()).contains("10000")
        );

    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        Class<Car> carClass = Car.class;
        Constructor<?> constructorArgs = stream(carClass.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == 2)
                .findFirst()
                .orElseThrow();

        Car carInstance = (Car) constructorArgs.newInstance("소나타", 10000);

        assertAll(
                () -> assertThat(carInstance.testGetName()).contains("소나타"),
                () -> assertThat(carInstance.testGetPrice()).contains("10000")
        );

    }

}
