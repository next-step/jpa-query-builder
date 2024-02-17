package persistence.study.step1;

import annotation.PrintView;
import domain.Car;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final Class<Car> clazz = Car.class;
    private static final String TEST_WORD = "test";

    @DisplayName("Car 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.")
    @Test
    void showClass() {
        logger.info("모든 필드");
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> {
                            logger.info("필드명 = {}", field.getName());
                            logger.info("접근 제어자 = {}", field.getModifiers());
                        }
                );

        logger.info("모든 생성자");
        Arrays.stream(clazz.getConstructors())
                .forEach(constructor -> {
                    logger.info("생성자명 = {}", constructor.getName());
                    logger.info("접근 제어자 = {}", constructor.getModifiers());

                    Arrays.stream(constructor.getParameterTypes())
                            .forEach(parameter -> logger.info("생성자 파라미터 데이터 타입 : {}", parameter.getName()));
                });

        logger.info("모든 메소드");
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(method -> {
                    logger.info("메소드명 = {}", method.getName());
                    logger.info("접근 제어자 = {}", method.getModifiers());

                    Arrays.stream(method.getParameterTypes())
                            .forEach(parameter -> logger.info("메소드 파라미터 데이터 타입 : {}", parameter.getName()));
                });
    }

    @DisplayName("Car 클래스에서 test 로 시작하는 메소드만 Java Reflection 을 활용해 실행한다.")
    @Test
    void testMethodRun() {
        Method[] methods = clazz.getDeclaredMethods();

        Stream<Object> result = Arrays.stream(methods)
                .filter(method -> method.getName().startsWith(TEST_WORD))
                .map(method -> {
                    try {
                        Object invokedMethod = method.invoke(clazz.getDeclaredConstructor().newInstance());
                        logger.info("메소드 = {}", invokedMethod);
                        return invokedMethod;
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });

        assertThat(result).hasSize(2);
    }

    @DisplayName("Car 클래스에서 @PrintView 애노테이션으로 설정되어 있는 메소드만 Java Reflection 을 활용해 실행한다.")
    @Test
    void testAnnotationMethodRun() {
        Method[] methods = clazz.getDeclaredMethods();

        Stream<Object> result = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(PrintView.class))
                .map(method -> {
                    try {
                        Object invokedMethod = method.invoke(clazz.getDeclaredConstructor().newInstance());
                        logger.info("메소드 = {}", invokedMethod);
                        return invokedMethod;
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });

        assertThat(result).hasSize(1);
    }

    @DisplayName("Reflection API 를 활용해 다음 Car 클래스의 name 과 price 필드에 값을 할당한다.")
    @Test
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException {
        Car car = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        String carName = "페라리";
        int carPrice = 1_000_000_000;

        Arrays.stream(fields)
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        switch (field.getName()) {
                            case "name" -> field.set(car, carName);
                            case "price" -> field.set(car, carPrice);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        logger.info("car = {}", car);
        assertThat(car.getName()).isEqualTo(carName);
        assertThat(car.getPrice()).isEqualTo(carPrice);
    }

    @DisplayName("자바 Reflection API 를 활용해 Car 인스턴스를 생성한다.")
    @Test
    void constructorWithArgs() {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        String carName = "포르쉐";
        int carPrice = 1_000_000;

        Arrays.stream(constructors)
                .forEach(constructor -> {
                    try {
                        if (constructor.getParameterTypes().length == 0) {
                            Car car = (Car) constructor.newInstance();
                            logger.info("기본 생성자 = {}", car);
                            assertThat(car).isInstanceOf(Car.class);
                        } else {
                            Car car = (Car) constructor.newInstance(carName, carPrice);
                            logger.info("매개변수가 있는 생성자 = {}", car);
                            assertThat(car.getName()).isEqualTo(carName);
                            assertThat(car.getPrice()).isEqualTo(carPrice);
                        }
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
