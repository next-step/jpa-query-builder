package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
                .toList();

        Car car = carClass.getDeclaredConstructor().newInstance();
        List<String> methodResults = testMethods.stream()
                .map(testMethod -> {
                    try {
                        return (String) testMethod.invoke(car);
                    } catch (Exception e) {
                        fail("Exception occurred invoking test method : {}", testMethod.getName());
                    }
                    return null;
                }).toList();

        assertThat(methodResults.size()).isEqualTo(2);
        assertThat(methodResults).allSatisfy(methodResult -> assertThat(methodResult).startsWith("test"));
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메서드 실행")
    void testAnnotationMethodRun() throws Exception {
        Class<Car> carClass = Car.class;

        Method[] declaredMethods = carClass.getDeclaredMethods();
        List<Method> annotationMethods = stream(declaredMethods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .toList();

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

}
