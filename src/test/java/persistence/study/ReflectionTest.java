package persistence.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("클래스 정보 출력")
    void showClass() {
        Class<Car> carClass = Car.class;
        logger.info(carClass.getName());

        Field[] declaredFields = carClass.getDeclaredFields();
        logger.info("객체의 모든 필드 출력");
        for (Field field : declaredFields) {
            logger.info("field name : " + field.getName());
        }

        Constructor<?>[] constructors = carClass.getDeclaredConstructors();
        logger.info("객체의 모든 생성자 출력");
        for (Constructor constructor : constructors) {
            logger.info(constructor.getName() + " " + constructor.getModifiers() + " " + Arrays.toString(
                constructor.getParameterTypes()));
        }

        Method[] declaredMethods = carClass.getDeclaredMethods();
        logger.info("객체의 모든 메서드 출력");
        for (Method method : declaredMethods) {
            logger.info(
                method.getName() + " " + method.getModifiers() + " " + Arrays.toString(method.getParameterTypes()));
        }
    }

    @Test
    @DisplayName("test로 시작하는 메소드 실행")
    void testMethodRun() {
        //given
        final String PREFIX = "test";
        Car car = createCarWithDefaultConstructor();

        // when
        Method[] declaredMethods = Car.class.getDeclaredMethods();
        List<Method> methods = Arrays.stream(declaredMethods)
                                     .filter(method -> method.getName().startsWith(PREFIX)).collect(Collectors.toList());
        methods.forEach(method -> invokeMethod(method, car));

        // then
        assertAll(
            () -> assertThat(methods).hasSize(2),
            () -> assertThat(methods.stream().map(Method::getName).sorted()).containsExactly(
                "testGetName", "testGetPrice"
            )
        );
    }

    @Test
    @DisplayName("PrintView Annotation method 실행")
    void invokePrintViewAnnotationMethod() {
        // given
        Car car = createCarWithDefaultConstructor();

        Method[] declaredMethods = Car.class.getDeclaredMethods();
        List<Method> methods = Arrays.stream(declaredMethods)
                                     .filter(method -> method.isAnnotationPresent(PrintView.class)).collect(Collectors.toList());
        methods.forEach(method -> invokeMethod(method, car));

        // then
        assertAll(
            () -> assertThat(methods).hasSize(1),
            () -> assertThat(methods.stream().map(Method::getName).collect(Collectors.toList())).containsExactly(
                "printView"
            )
        );
    }


    @Test
    @DisplayName("private field에 값 할당")
    void privateField() {
        // given
        Car car = createCarWithDefaultConstructor();
        final String NAME_TEST_VALUE = "test";
        final int PRICE_TEST_VALUE = 111;

        Field[] declaredFields = Car.class.getDeclaredFields();
        List<Field> privateFields = Arrays.stream(declaredFields)
                                          .filter(it -> it.getModifiers() == Modifier.PRIVATE)
                                          .collect(Collectors.toList());
        privateFields.forEach(
            field -> {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    fillStringArgument(car, field, NAME_TEST_VALUE);

                } else if (field.getType() == int.class) {
                    fillIntArgument(car, field, PRICE_TEST_VALUE);
                }
            }
        );

        assertAll(
            () -> assertThat(getNameField(car)).isEqualTo(NAME_TEST_VALUE),
            () -> assertThat(getPriceField(car)).isEqualTo(PRICE_TEST_VALUE)
        );
    }

    @Test
    void constructorWithArgs() {
        Class<Car> carClass = Car.class;
        final String NAME_TEST_VALUE = "test";
        final int PRICE_TEST_VALUE = 111;
        Constructor<?> constructorWithTwoParameter = Arrays.stream(carClass.getDeclaredConstructors())
                                                           .filter(it -> it.getParameterCount() == 2)
                                                           .findFirst().get();
        Car car = createCarWithParameter((Constructor<Car>) constructorWithTwoParameter, NAME_TEST_VALUE,
            PRICE_TEST_VALUE);

        assertAll(
            () -> assertThat(getNameField(car)).isEqualTo(NAME_TEST_VALUE),
            () -> assertThat(getPriceField(car)).isEqualTo(PRICE_TEST_VALUE)
        );
    }

    private Car createCarWithDefaultConstructor() {
        Class<Car> carClass = Car.class;
        try {
            return carClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Create Car Fail");
        }
    }

    private Car createCarWithParameter(Constructor<Car> constructor, String name, int price) {
        try {
            return constructor.newInstance(name, price);
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException("Create Car Fail");
        }
    }

    private void invokeMethod(Method method, Car car) {
        try {
            Object invoke = method.invoke(car);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void fillIntArgument(Car car, Field field, int value) {
        field.setAccessible(true);
        try {
            field.set(car, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("숫자값 할당에 실패했습니다");
        }
    }

    private void fillStringArgument(Car car, Field field, String value) {
        field.setAccessible(true);
        try {
            field.set(car, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("문자열 할당에 실패했습니다");
        }
    }

    private int getPriceField(Car car) {
        try {
            Field field = Car.class.getDeclaredField("price");
            field.setAccessible(true);
            return (int) field.get(car);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            throw new RuntimeException("getPriceField 조회 실패");
        }
    }

    private String getNameField(Car car) {
        try {
            Field field = Car.class.getDeclaredField("name");
            field.setAccessible(true);
            return (String) field.get(car);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            throw new RuntimeException("getNameField 조회 실패");
        }
    }
}
