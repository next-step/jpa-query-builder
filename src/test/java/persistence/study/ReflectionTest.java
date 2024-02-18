package persistence.study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);
    private static final Class<Car> carClass = Car.class;


    @DisplayName("Car 객체 정보 가져오기")
    @Test
    void showClass() {
        logger.debug("className: {}",carClass.getName());

        List<String> fieldNames = Arrays.stream(carClass.getDeclaredFields())
            .map(Field::getName)
            .toList();
        logger.debug("fields: {}", fieldNames);

        List<String> constructorInfos = Arrays.stream(carClass.getDeclaredConstructors())
            .map(constructor -> String.format(
                    "{constructorName: %s / parameterCount: %s / parameterTypes: %s}",
                    constructor.getName(),
                    constructor.getParameterCount(),
                    Arrays.toString(constructor.getParameterTypes())
                )
            )
            .toList();
        logger.debug("constructors: {}", constructorInfos);

        List<String> methodInfos = Arrays.stream(carClass.getDeclaredMethods())
            .map(method -> String.format(
                    "{methodName: %s / parameterCount: %s / parameterTypes: %s}",
                    method.getName(),
                    method.getModifiers(),
                    method.getParameterCount(),
                    Arrays.toString(method.getParameterTypes())
                )
            )
            .toList();
        logger.debug("methods: {}", methodInfos);
    }

    @DisplayName("Car 객체에 있는 test로 시작하는 메서드들을 실행한다.")
    @Test
    void testMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String targetMethodName = "test";
        Car carInstance = carClass.getConstructor().newInstance();

        List<Object> results = Arrays.stream(carClass.getMethods())
            .filter(method -> method.getName().startsWith(targetMethodName))
            .map(method -> {
                try {
                    return method.invoke(carInstance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        assertThat(results).hasSize(2);
        assertThat(results.contains("test : null")).isTrue();
        assertThat(results.contains("test : 0")).isTrue();
    }

    @DisplayName("Car 객체에 있는 PrintView 어노테이션이 적용된 메서드들을 실행한다.")
    @Test
    void testAnnotationMethodRun() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Car carInstance = carClass.getConstructor().newInstance();

        List<Object> results = Arrays.stream(carClass.getMethods())
            .filter(carMethod -> carMethod.isAnnotationPresent(PrintView.class))
            .map(carMethod -> {
                try {
                    return carMethod.invoke(carInstance);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        assertThat(results).hasSize(1);
    }

    @DisplayName("Car 객체에 있는 private 필드에 값을 set한다.")
    @Test
    void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String name = "자동차";
        int price = 1000;

        Car carInstance = carClass.getConstructor().newInstance();
        Field[] declaredFields = carClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals("name")) {
                declaredField.setAccessible(true);
                declaredField.set(carInstance, name);
            }

            if (declaredField.getName().equals("price")) {
                declaredField.setAccessible(true);
                declaredField.set(carInstance, price);
            }
        }

        assertThat(carInstance.getName()).isEqualTo(name);
        assertThat(carInstance.getPrice()).isEqualTo(price);
    }

    @DisplayName("인자가 있는 Car 생성자를 사용해 객체를 생성한다.")
    @Test
    void constructorWithArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        String name = "자동차";
        int price = 1000;

        Constructor<?> constructor = Arrays.stream(carClass.getConstructors()).filter(carConstructor -> carConstructor.getParameterTypes().length == 2)
            .findFirst()
            .orElseThrow();

        Car carInstance = (Car) constructor.newInstance(name, price);
        assertThat(carInstance).isNotNull();
        assertThat(carInstance.getName()).isEqualTo(name);
        assertThat(carInstance.getPrice()).isEqualTo(price);
    }

}
