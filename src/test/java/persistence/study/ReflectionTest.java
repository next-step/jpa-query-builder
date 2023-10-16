package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("요구사항 1 - Car 클래스 정보 출력")
    void testCarPrint() throws NoSuchMethodException {
        //given
        Class<Car> carClass = Car.class;

        //when
        //1. 필드 정보를 출력
        Field[] fields = carClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> logger.info("fields : {}", field));

        //2. 생성자 정보를 출력
        Constructor<Car> carConstructor = carClass.getConstructor();
        String constructorName = carConstructor.getName();
        logger.info("carConstructor : {}", constructorName);

        //3. 메소드 정보를 출력
        Method[] methods = carClass.getMethods();
        Arrays.stream(methods).forEach(method -> logger.info("method : {}", method));

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(fields).isNotEmpty();
            softAssertions.assertThat(constructorName).isNotNull();
            softAssertions.assertThat(methods).isNotEmpty();
        });
    }

    @Test
    @DisplayName("요구사항 2 - test로 시작하는 메소드 실행")
    void testMethodRun() {
        //given
        final String METHOD_START_WORD = "test";

        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();

        //when
        String[] result = Arrays.stream(methods).filter(method -> method.getName().startsWith(METHOD_START_WORD))
                .map(method -> execMethod(method, carClass))
                .toArray(String[]::new);

        //then
        assertThat(result).anyMatch(string -> string.contains(METHOD_START_WORD));
    }

    @Test
    @DisplayName("요구사항 3 - @PrintView 애노테이션 메소드 실행")
    void testAnnotationMethodRun() {
        //given
        Class<Car> carClass = Car.class;
        Method[] methods = carClass.getDeclaredMethods();

        //when
        Arrays.stream(methods).filter(method -> method.isAnnotationPresent(PrintView.class))
                .forEach(method -> {
                    execMethod(method, carClass);
                });
    }

    @Test
    @DisplayName("요구사항 4 - private field에 값 할당")
    void privateFieldAccess() throws Exception {
        //given
        final String name = "소나타";
        final int price = 3000;

        Class<Car> carClass = Car.class;
        Car result = carClass.getDeclaredConstructor().newInstance();

        Field[] fields = carClass.getDeclaredFields();

        //when
        Arrays.stream(fields)
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .forEach(field -> {
                    field.setAccessible(true);

                    parseFiled(field, result, name, price);
                });

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getName()).isEqualTo(name);
            softAssertions.assertThat(result.getPrice()).isEqualTo(price);
        });
    }

    private static void parseFiled(Field field, Car result, String name, int price) {
        try {
            if ("name".equals(field.getName())) {
                field.set(result, name);
            } else if ("price".equals(field.getName())) {
                field.set(result, price);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("요구사항 5 - 인자를 가진 생성자의 인스턴스 생성")
    void constructorWithArgs() throws Exception {
        //given
        final String name = "소나타";
        final int price = 3000;

        Class<Car> carClass = Car.class;

        //when
        Car result = carClass.getDeclaredConstructor(String.class, int.class)
                .newInstance(name, price);

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getName()).isEqualTo(name);
            softAssertions.assertThat(result.getPrice()).isEqualTo(price);
        });
    }

    private String execMethod(Method method, Class<Car> carClass) {
        try {
            String execResult = (String) method.invoke(carClass.newInstance());
            logger.info(execResult);

            return execResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
