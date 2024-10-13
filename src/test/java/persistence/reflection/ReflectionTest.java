package persistence.reflection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.study.Car;
import persistence.study.PrintView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;


public class ReflectionTest {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("Car 객체 정보들 가져오기")
    void showClass() {
        Class<Car> carClass = Car.class;
        // 클래스 정보출력
        log.info(carClass.getSimpleName());

        // 필드 정보 출력
        Arrays.stream(carClass.getDeclaredFields()).forEach(field -> log.info(field.getName()));

        // 생성자 정보 출력
        Arrays.stream(carClass.getConstructors()).forEach(constructor -> {
            log.info("Constructor: {}", constructor.getName());
            Parameter[] parameters = constructor.getParameters();
            if (parameters.length == 0) {
                log.info("No parameters");
            } else {
                log.info("parameters ");
                Arrays.stream(parameters).forEach(parameter -> log.info("Type: {}, Name: {}", parameter.getType().getName(), parameter.getName()));
            }
        });

        // 메서드 정보 출력
        Arrays.stream(carClass.getDeclaredMethods()).forEach(method -> log.info("Method is {}", method.getName()));

    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void showTestMethods() {
        Car genesisCar = new Car("Genesis", 50_000_000);
        Arrays.stream(genesisCar.getClass().getDeclaredMethods()).map(method -> {
            Object methodName = null;
            if (method.getName().startsWith("test")) {
                try {
                    methodName = method.invoke(genesisCar);

                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
            return methodName;
        }).filter(Objects::nonNull).forEach(name -> log.info(name.toString()));
    }

    @Test
    @DisplayName("@PrintView 애노테이션 메소드 실행")
    void showAnnotation() throws NoSuchMethodException {
        Class<Car> carClass = Car.class;
        Method printView = carClass.getDeclaredMethod("printView");
        assertThat(printView.isAnnotationPresent(PrintView.class)).isEqualTo(true);
    }
}
