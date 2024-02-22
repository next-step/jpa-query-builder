package persistence.study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LoggingPermission;
import java.util.stream.Stream;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    @DisplayName("Car 객체 정보를 출력하라")
    void showClass() {
        Class<Car> carClass = Car.class;
        Assertions.assertEquals(carClass.getName(), "persistence.study.Car");
    }

    @Test
    @DisplayName("test로 시작하는 메소드를 실행하라")
    void testMethodRun() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        // given
        Class<Car> carClass = Car.class;
        List<String> expected = Stream.of("test : 기아자동차", "test : 1000").sorted().toList();

        // when
        List<Method> methodsToExecute = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .toList();

        List<String> resultMessages = new java.util.ArrayList<>(List.of());
        for (Method method : methodsToExecute) {
            String result = (String) method.invoke(carClass.getDeclaredConstructor(String.class, int.class).newInstance("기아자동차", 1000));
            resultMessages.add(result);
        }

        // then
        Assertions.assertIterableEquals(resultMessages.stream().sorted().toList(), expected);

    }

    @Test
    @DisplayName("@PrintView 애노테이션이 붙은 메소드를 실행하라")
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // given
        Class<Car> carClass = Car.class;
        System.setOut(new PrintStream(outputStreamCaptor));

        // when
        List<Method> methodsToExecute = Arrays.stream(carClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .toList();

        for (Method method : methodsToExecute) {
            method.invoke(carClass.getDeclaredConstructor(String.class, int.class).newInstance("기아자동차", 1000));
            Assertions.assertEquals("자동차 정보를 출력합니다.", outputStreamCaptor.toString().trim());
        }
    }

    @Test
    @DisplayName("private field에 값을 할당하라")
    void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Car car = new Car();

        Field name = car.getClass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(car, "기아자동차");

        Field price = car.getClass().getDeclaredField("price");
        price.setAccessible(true);
        price.set(car, 1000);

        logger.debug(car.getName());
        logger.debug(String.valueOf(car.getPrice()));
    }

    @Test
    @DisplayName("인자를 가진 생성자의 인스턴스 생성하라")
    void constructorWithArgs() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Car.class.getDeclaredConstructor(String.class, int.class).newInstance("기아자동차", 10000);
    }
}
