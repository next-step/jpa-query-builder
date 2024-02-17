package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @DisplayName("Car 객체 정보 가져오기")
    @Test
    void showClass() {
        final Class<Car> carClass = Car.class;

        Arrays.stream(carClass.getDeclaredFields()).forEach(System.out::println);
        Arrays.stream(carClass.getConstructors()).forEach(System.out::println);
        Arrays.stream(carClass.getDeclaredMethods()).forEach(System.out::println);
    }

    @DisplayName("test로 시작하는 메소드 실행한다.")
    @Test
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String name = "simpson";
        int age = 31;
        final Class<Car> carClass = Car.class;
        final Car car = carClass.getConstructor(String.class, int.class).newInstance(name, age);

        for (Method method : carClass.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                final String result = (String) method.invoke(car);
                logger.info(result);
            }
        }
    }
}
